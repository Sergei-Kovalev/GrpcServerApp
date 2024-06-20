package kovalev.grpc.server.controller;

import kovalev.grpc.server.dto.KafkaMessageDTO;
import kovalev.grpc.server.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class KafkaProducerController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<String> sendUserToKafkaBroker(@RequestBody KafkaMessageDTO kafkaMessageDTO) {
        String responseMessage = kafkaProducerService.sendMessageAboutRequestToKafka(kafkaMessageDTO);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
