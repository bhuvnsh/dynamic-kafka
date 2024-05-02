package com.bhuvanesh.blog.dynamickafka.service;

import com.bhuvanesh.blog.dynamickafka.dto.MessageDTO;
import com.bhuvanesh.blog.dynamickafka.dto.ProducerDTO;

public interface ProducerService {
  String createProducer();
  String produceMessage();
}
