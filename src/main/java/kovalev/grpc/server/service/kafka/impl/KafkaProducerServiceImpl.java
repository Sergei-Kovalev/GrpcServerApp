package kovalev.grpc.server.service.kafka.impl;

import kovalev.grpc.server.service.kafka.KafkaProducerService;
import kovalev.grpc.server.service.kafka.event.EventForKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, EventForKafka> kafkaTemplate;

    @Override
    public void sendMessageAboutRequestToKafka(EventForKafka eventForKafka) {
        kafkaTemplate.send("requested-users-from-server-db", eventForKafka);
    }
}
