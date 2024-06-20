package kovalev.grpc.server.service;

import kovalev.grpc.server.dto.KafkaMessageDTO;

public interface KafkaProducerService {
    String sendMessageAboutRequestToKafka(KafkaMessageDTO kafkaMessageDTO);
}
