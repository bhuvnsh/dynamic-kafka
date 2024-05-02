package com.bhuvanesh.blog.dynamickafka.controller;

import org.springframework.http.ResponseEntity;


public interface ConsumerApi {

  ResponseEntity<String> createConsumer();
}
