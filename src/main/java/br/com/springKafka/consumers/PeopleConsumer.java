package br.com.springKafka.consumers;

import br.com.springkafka.People;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
@Component
public class PeopleConsumer {


    @KafkaListener(topics = "${topic.name}")
    public void consumer(ConsumerRecord<String, People> record, Acknowledgment ack){
        var people = record.value();
        System.out.println("PEOPLE consumer: " + people.toString() );
        ack.acknowledge();
    }
}
