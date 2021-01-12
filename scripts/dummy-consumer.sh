BIN="kafka_2.12-2.0.0/bin"
${BIN}/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic foo --from-beginning
