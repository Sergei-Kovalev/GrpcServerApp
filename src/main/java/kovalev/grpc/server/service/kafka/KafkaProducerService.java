package kovalev.grpc.server.service.kafka;

import kovalev.grpc.server.service.kafka.event.EventForKafka;

public interface KafkaProducerService {
    void sendMessageAboutRequestToKafka(EventForKafka eventForKafka);
}
