package com.hyan.electionservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.service.ElectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ElectionSessionClosedProducer {

    private Logger logger = LoggerFactory.getLogger(ElectionSessionClosedProducer.class);

    private RabbitTemplate rabbitTemplate;

    private ElectionService electionService;

    private ObjectMapper objectMapper;

    @Value("backend.election")
    private String exchange;

    @Value("session-closed")
    private String rountigKey;

    @Autowired
    public ElectionSessionClosedProducer(RabbitTemplate rabbitTemplate, ElectionService electionService, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.electionService = electionService;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 60000)
    public void produce() {
        logger.info("Iniciando a busca por Sessẽs encerradas.");
        electionService.findSessionCloesed()
                .collectList()
                .map(x -> {
                    rabbitTemplate.send(exchange, rountigKey, new Message(convertObjectToByte(x), new MessageProperties()));
                    return x;
                }).then();

    }

    private byte[] convertObjectToByte(List<Election> election) {
        try {
            return objectMapper.writeValueAsString(election).getBytes();
        } catch (JsonProcessingException e) {
            logger.error("Erro ao enviar '" + election.toString() + "'. Não foi possível converter o objeto para byte.", e);
            return null;
        }
    }
}
