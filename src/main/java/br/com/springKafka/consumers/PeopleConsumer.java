package br.com.springKafka.consumers;

import br.com.springKafka.domains.Book;
import br.com.springKafka.repositories.PeopleRepository;
import br.com.springkafka.People;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PeopleConsumer {

    @Autowired
    private PeopleRepository peopleRepository;


    @KafkaListener(topics = "${topic.name}")
    public void consumer(ConsumerRecord<String, People> record, Acknowledgment ack){
        var people = record.value();

        var peopleEntity = br.com.springKafka.domains.People.builder().build();
        peopleEntity.setId(people.getId().toString());
        peopleEntity.setCpf(people.getCpf().toString());
        peopleEntity.setName(people.getName().toString());
        peopleEntity.setBooks(people.getBooks()
                .stream()
                .map( bookString -> Book.builder()
                        .people(peopleEntity)
                        .titulo(bookString.toString())
                        .build()).collect(Collectors.toList()));

        peopleRepository.save(peopleEntity);

        System.out.println("PEOPLE consumer: " + people.toString() );
        ack.acknowledge();
    }
}
