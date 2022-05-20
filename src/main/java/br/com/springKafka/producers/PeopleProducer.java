package br.com.springKafka.producers;

import br.com.springkafka.People;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PeopleProducer {

    private static Logger log = LoggerFactory.getLogger(PeopleProducer.class);
    private final String topic;
    private final KafkaTemplate<String, People> kafkaTemplate;

    public PeopleProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, People> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(People people){
        kafkaTemplate.send(topic, people).addCallback(
                success -> log.info("Mensagem enviada"),
                failure -> log.info("Falha ao enviar mensagem!")
        );
    }
}
