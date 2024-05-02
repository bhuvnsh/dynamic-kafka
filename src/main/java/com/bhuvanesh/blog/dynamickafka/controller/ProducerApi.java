package com.bhuvanesh.blog.dynamickafka.controller;

import org.springframework.http.ResponseEntity;


public interface ProducerApi {


  ResponseEntity<String> createProducer();


  ResponseEntity<String> produceMessage();
}
