package kovalev.grpc.server.service.impl;

import kovalev.grpc.server.dto.KafkaMessageDTO;
import kovalev.grpc.server.service.KafkaProducerService;
import kovalev.grpc.server.service.event.EventForKafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, EventForKafka> kafkaTemplate;

    @Override
    public String sendMessageAboutRequestToKafka(KafkaMessageDTO kafkaMessageDTO) {

        EventForKafka event = new EventForKafka(kafkaMessageDTO.getUserID(), kafkaMessageDTO.getRequestTime());

        kafkaTemplate.send("requested-users-from-server-db", event);

        return "Client requested user with id = " + event.getUserID() + " at " + event.getRequestTime().toString();
    }
}
