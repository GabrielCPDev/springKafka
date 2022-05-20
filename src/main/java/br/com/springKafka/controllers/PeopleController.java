package br.com.springKafka.controllers;

import br.com.springKafka.domains.dtos.PeopleDTO;
import br.com.springKafka.producers.PeopleProducer;
import br.com.springkafka.People;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/peoples")
@AllArgsConstructor
public class PeopleController {

    private final PeopleProducer peopleProducer;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody PeopleDTO dto){
        var id = UUID.randomUUID().toString();

        var message = People.newBuilder()
                .setId(id)
                .setName(dto.getName())
                .setCpf(dto.getCpf())
                .setBooks(dto.getBooks().stream().map( book -> (CharSequence) book).collect(Collectors.toList()))
                .build();
        peopleProducer.sendMessage(message);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
