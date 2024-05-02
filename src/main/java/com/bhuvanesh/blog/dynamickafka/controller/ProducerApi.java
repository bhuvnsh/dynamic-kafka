package com.bhuvanesh.blog.dynamickafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/producer")
public interface ProducerApi {

  @GetMapping("/create")
  ResponseEntity<String> createProducer();

  @GetMapping("/produce")
  ResponseEntity<String> produceMessage();
}
