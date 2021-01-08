//package it.unibo.big
//
//import org.apache.kafka.clients.consumer._
//import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
//
//import java.util
//import java.util.Properties
//
//trait IProducer {
//  def process(message: String): Unit
//}
//
//object IProducer {
//  def write(producer: KafkaProducer[String, String], topic: String, message: String): Unit = {
//    val pr = new ProducerRecord[String, String](topic, message)
//    producer.send(pr)
//  }
//
//  def createConfig(servers: String): Properties = {
//    val props = new Properties()
//    props.put("bootstrap.servers", servers)
//    props.put("acks", "all")
//    props.put("retries", 0)
//    props.put("batch.size", 1000)
//    props.put("linger.ms", 1)
//    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
//    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
//    props
//  }
//}
//
//object Consumer {
//  def createConfig(servers: String): Properties = {
//    val props = new Properties()
//    props.put("bootstrap.servers", servers)
//    props.put("acks", "all")
//    props.put("retries", 0)
//    props.put("batch.size", 1000)
//    props.put("linger.ms", 1)
//    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
//    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
//    props
//  }
//}
//
///**
// * As a first step, we need to read individual raw events from our Kafka topic raw-events.
// * In Kafka parlance, we need to write a consumer. Remember that in the preceding chapter,
// * we depended on the Kafka command-line tools to write events to a topic, and to
// * read events back out of that topic. In this chapter, we will write our own consumer in
// * Java, using the Kafka Java client library. we have defined a consumer that will read all the records from a given
// * Kafka topic and hand them over to the process method of the supplied producer. We
// * don’t need to worry about most of the consumer’s configuration properties, but note
// * the group.id, which lets us associate this app with a specific Kafka consumer group. We
// * could run multiple instances of our app all with the same group.id to share out the
// * topic’s events across all of our instances; by contrast, if each instance had a different
// * group.id, each instance would get all of Nile’s raw-events.
// *
// * @param servers servers
// * @param groupId groupId
// * @param topic   topic
// */
//class Consumer(servers: String, groupId: String, topic: String) {
//  val consumer = new KafkaConsumer[String, String](createConfig(servers, groupId))
//
//  def run(producer: IProducer): Unit = {
//    val topics: util.Collection[String] = util.Arrays.asList(topic)
//    consumer.subscribe(topics)
//    while (true) {
//      // val c = new java.util.function.Consumer[ConsumerRecord[String, String]]() {
//      //   override def accept(record: ConsumerRecord[String, String]): Unit = producer.process(record.value())
//      // }
//      // consumer.poll(100).forEach(record => producer.process(record.asInstanceOf[ConsumerRecord[String, String]].value()))
//      val f : java.util.function.Consumer[ConsumerRecord[String, String]] = { case record: ConsumerRecord[String, String] => producer.process(record.value()) }
//      consumer.poll(100).forEach(f)
//    }
//  }
//
//  def createConfig(servers: String, groupId: String): Properties = {
//    val props = new Properties()
//    props.put("bootstrap.servers", servers)
//    props.put("group.id", groupId)
//    props.put("enable.auto.commit", "true")
//    props.put("auto.commit.interval.ms", "1000")
//    props.put("auto.offset.reset", "earliest")
//    props.put("session.timeout.ms", "30000")
//    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
//    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
//    props
//  }
//}
//
///**
// * Remember that this section of the chapter is just a warm-up: we want to
// * pass the incoming raw-events into a second topic with the events themselves
// * untouched. We now know enough to implement a simple pass-through producer,
// *
// * @param servers servers
// * @param topic   topic
// */
//class PassthruProducer(val servers: String, val topic: String) extends IProducer {
//  val producer = new KafkaProducer[String, String](IProducer.createConfig(servers))
//
//  override def process(message: String): Unit = {
//    IProducer.write(this.producer, this.topic, message)
//  }
//}
//
//object StreamApp {
//  def main(args: Array[String]) {
//    val servers: String = args(0)
//    val groupId: String = args(1)
//    val inTopic: String = args(2)
//    val goodTopic: String = args(3)
//    val consumer: Consumer = new Consumer(servers, groupId, inTopic)
//    val producer: PassthruProducer = new PassthruProducer(servers, goodTopic)
//    consumer.run(producer)
//  }
//}