BIN="kafka_2.12-2.0.0/bin"
${BIN}/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic foo
# ${BIN}/kafka-topics.sh --list --zookeeper localhost:2181
${BIN}/kafka-topics.sh --describe --zookeeper localhost:2181 --topic foo