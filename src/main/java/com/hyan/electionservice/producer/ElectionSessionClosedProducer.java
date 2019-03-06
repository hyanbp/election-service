package com.hyan.electionservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyan.electionservice.entity.Election;
import com.hyan.electionservice.repository.ElectionRepository;
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

@Component
@EnableScheduling
public class ElectionSessionClosedProducer {

    private Logger logger = LoggerFactory.getLogger(ElectionSessionClosedProducer.class);

    private RabbitTemplate rabbitTemplate;

    private ElectionService electionService;

    private ObjectMapper objectMapper;

    private ElectionRepository electionRepository;

    @Value("backend.election")
    private String exchange;

    @Value("session-closed")
    private String rountigKey;

    @Autowired
    public ElectionSessionClosedProducer(RabbitTemplate rabbitTemplate, ElectionService electionService, ObjectMapper objectMapper, ElectionRepository electionRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.electionService = electionService;
        this.objectMapper = objectMapper;
        this.electionRepository = electionRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void produce() {
        logger.info("Iniciando a busca por Sessão de eleição encerradas.");
        Election election = electionService.getElectionSessionClosed();
        Message message = createMessageProducer(election);

        if (message == null) return;
        logger.info("Enviando Sessão de eleição: [{}] encerrada para exchange: [{}]", election.getId(), exchange);
        rabbitTemplate.send(exchange, rountigKey, message);

    }

    private byte[] convertObjectToByte(Election election) {
        try {
            return objectMapper.writeValueAsString(election).getBytes();
        } catch (JsonProcessingException e) {
            logger.error("Erro ao enviar '" + election.toString() + "'. Não foi possível converter o objeto para byte.", e);
            return null;
        }
    }


    private Message createMessageProducer(Election election) {
        if (election == null) {
            logger.info("Nenhuma Sessão de eleição encerrada encontrada.");
            return null;
        }

        electionService.setClosedSession(election);

        Message message = new Message(convertObjectToByte(election), new MessageProperties());
        message.getMessageProperties().setContentType("application/json");
        return message;
    }


}
