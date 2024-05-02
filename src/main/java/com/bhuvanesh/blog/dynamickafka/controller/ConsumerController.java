package com.bhuvanesh.blog.dynamickafka.controller;

import com.bhuvanesh.blog.dynamickafka.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConsumerController implements ConsumerApi {
  private final ConsumerService consumerService;

  @Override
  public ResponseEntity<String> createConsumer() {
    log.info("creating consumer");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(consumerService.createConsumer());
  }
}
