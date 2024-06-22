package kovalev.grpc.server.service.kafka.impl;

import kovalev.grpc.server.service.kafka.KafkaProducerService;
import kovalev.grpc.server.service.kafka.event.EventForKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, EventForKafka> kafkaTemplate;

    @Override
    public void sendMessageAboutRequestToKafka(EventForKafka eventForKafka)
            throws ExecutionException, InterruptedException {
        SendResult<String, EventForKafka> result =
                kafkaTemplate.send("requested-users-from-server-db", "No matter", eventForKafka).get();

        LOGGER.info("Message sent successfully to: topic {}, partition {}, offset {}",
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());

        LOGGER.info("Event for kafka sent: {}",
                result.getProducerRecord().value().toString());

        LOGGER.info("All OK");
    }
}
