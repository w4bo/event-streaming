package it.unibo.big.helloworld;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class HelloProducer {
    public static void main(String[] args) {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(Config.producerConfig)) {
            producer.send(new ProducerRecord<>("foo", "bar", "Hello, world! From Java"));
            System.out.println("Sent");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}