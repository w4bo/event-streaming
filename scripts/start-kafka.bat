kafka_2.12-2.0.0/bin/windows/zookeeper-server-start.bat kafka_2.12-2.0.0/config/zookeeper.properties
kafka_2.12-2.0.0/bin/windows/kafka-server-start.bat kafka_2.12-2.0.0/config/server.properties
kafka_2.12-2.0.0/bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning