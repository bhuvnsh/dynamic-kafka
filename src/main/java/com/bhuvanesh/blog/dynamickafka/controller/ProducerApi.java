package com.bhuvanesh.blog.dynamickafka.controller;

import com.bhuvanesh.blog.dynamickafka.dto.MessageDTO;
import com.bhuvanesh.blog.dynamickafka.dto.ProducerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public interface ProducerApi {


  ResponseEntity<String> createProducer();


  ResponseEntity<String> produceMessage();
}
