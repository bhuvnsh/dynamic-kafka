package com.bhuvanesh.blog.dynamickafka.controller;

import com.bhuvanesh.blog.dynamickafka.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProducerController implements ProducerApi {
  private final ProducerService producerService;

  @Override
  public ResponseEntity<String> createProducer() {
    log.info("creating producer");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(producerService.createProducer());
  }

  @Override
  public ResponseEntity<String> produceMessage() {
    log.info("producing message");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(producerService.produceMessage());
  }
}
