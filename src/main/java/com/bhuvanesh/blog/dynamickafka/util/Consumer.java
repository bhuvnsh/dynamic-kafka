package com.bhuvanesh.blog.dynamickafka.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Consumer implements MessageListener<String, Object> {
  @Override
  public void onMessage(ConsumerRecord<String, Object> record) {
    log.info("RECORD PROCESSING: {}", record.value());
  }
}
