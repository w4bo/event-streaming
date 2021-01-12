BIN="kafka_2.12-2.0.0/bin"
${BIN}/kafka-console-producer.sh --broker-list localhost:9092 --topic foo
echo "Hello, World! from Bash"