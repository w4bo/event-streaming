package it.unibo.big.helloworld;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;

public class HelloConsumer {
    public static void main(String[] args) {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(Config.consumerConfig)) {
            consumer.subscribe(Arrays.asList("foo"));
            System.out.println("Submitted");
            while (true) {
                consumer.poll(100).forEach(m -> System.out.println("Got message " + m.value()));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
