package it.unibo.big.producer

import com.beust.klaxon.JsonObject
import it.unibo.big.helloworld.Config
import krangl.DataFrame
import krangl.readCSV
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

/**
 * A producer for weather and soilhumidity
 * @param args filename and sampling pace (in milliseconds, i.e. 1 / sampling frequency)
 */
fun main(args: Array<String>) {
    // get the file name
    val filename: String = if (args.isEmpty()) "soilhumidity" else args[0]
    // get the frequency
    val freq: Long = if (args.isEmpty()) 100L else args[1].toLong()
    // read the input data and sort them by timestamp
    val df: DataFrame = DataFrame.readCSV("$filename.csv").sortedBy("timestamp")
    KafkaProducer<String, String>(Config.producerConfig).use { producer ->
        // for each row
        df.rows.forEach { r ->
            // transform the row into a JsonObject
            val obj = JsonObject()
            df.names.forEach { obj[it] = r[it] }
            // send the event (i.e., the object)
            producer.send(ProducerRecord(filename, obj.toString()))
            // ... and sleep to slow down the process
            Thread.sleep(freq)
        }
    }
}