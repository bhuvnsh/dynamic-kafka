package com.bhuvanesh.blog.dynamickafka.service.implement;

import com.bhuvanesh.blog.dynamickafka.dto.ConsumerDTO;
import com.bhuvanesh.blog.dynamickafka.listener.KafkaListenerContainerManager;
import com.bhuvanesh.blog.dynamickafka.listener.ListenerKafkaProperties;
import com.bhuvanesh.blog.dynamickafka.service.ConsumerService;
import com.bhuvanesh.blog.dynamickafka.util.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
  private final KafkaListenerContainerManager<String, String> kafkaListenerManager;
  private final Consumer consumer;
  private final AtomicInteger index = new AtomicInteger(1);
  @Override
  public String createConsumer() {
    ListenerKafkaProperties kafkaProperties = ListenerKafkaProperties.builder()
                                                    .topic("my-topic")
                                                    .groupId("group-id-"+ index.getAndIncrement())
                                                    .listenerId(UUID.randomUUID().toString())
                                                    .messageListener(consumer)
                                                    .build();
    kafkaListenerManager.registerListener(kafkaProperties,true);
    return "created and started listener";
  }
}
