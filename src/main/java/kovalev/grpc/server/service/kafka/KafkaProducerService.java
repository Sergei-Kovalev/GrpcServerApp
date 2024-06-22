package kovalev.grpc.server.service.kafka;

import kovalev.grpc.server.service.kafka.event.EventForKafka;

import java.util.concurrent.ExecutionException;

public interface KafkaProducerService {
    void sendMessageAboutRequestToKafka(EventForKafka eventForKafka) throws ExecutionException, InterruptedException;
}
