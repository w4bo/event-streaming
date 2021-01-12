BIN="kafka_2.12-2.0.0/bin"
${BIN}/zookeeper-server-start.sh kafka_2.12-2.0.0/config/zookeeper.properties &
${BIN}/kafka-server-start.sh kafka_2.12-2.0.0/config/server.properties &