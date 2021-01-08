package it.unibo.big;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HelloProducer {
    private static final Logger logger = LoggerFactory.getLogger("HelloProducer");
    public static void main(String[] args) {
        // if (args.length != 2) {
        //     System.out.println("Please provide command line arguments: topicName numEvents");
        //     System.exit(-1);
        // }
        final String topicName = "foo"; // args[0];
        final int numEvents = 3; // Integer.valueOf(args[1]);
        logger.info("Starting HelloProducer...");
        logger.debug("topicName=" + topicName + ", numEvents=" + numEvents);
        logger.trace("Creating Kafka Producer...");
        final Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "HelloProducer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        try (KafkaProducer<Integer, String> producer = new KafkaProducer<>(props)) {
            logger.trace("Start sending messages...");
            for (int i = 1; i <= numEvents; i++) {
                producer.send(new ProducerRecord<>(topicName, i, "Simple Message-" + i));
            }
        } catch (final KafkaException e) {
            logger.error("Exception occurred – Check log for more details.\n" + e.getMessage());
        } finally {
            logger.info("Finished HelloProducer – Closing Kafka Producer.");
        }
    }
}