package com.bhuvanesh.blog.dynamickafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/consumer")
public interface ConsumerApi {

  @GetMapping("/create")
  ResponseEntity<String> createConsumer();
}
