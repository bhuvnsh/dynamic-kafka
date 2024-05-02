package com.bhuvanesh.blog.dynamickafka.service.implement;

import com.bhuvanesh.blog.dynamickafka.dto.MessageDTO;
import com.bhuvanesh.blog.dynamickafka.dto.ProducerDTO;
import com.bhuvanesh.blog.dynamickafka.producer.KafkaProducerManager;
import com.bhuvanesh.blog.dynamickafka.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService {
  private final KafkaProducerManager producerManager;
  private RoutingKafkaTemplate template;

  @Override
  public String createProducer() {
    instantiateKafkaProducer();
    return "created producer successfully";
  }

  @Override
  public String produceMessage() {
    String msg = UUID.randomUUID().toString();
    log.info("sending msg: {}", msg);
    template.send("my-topic", msg);
    return "produced message";
  }

  @Bean
  public KafkaTemplate<Object, Object> instantiateKafkaProducer() {
    this.template = producerManager
        .routingTemplate(".*-topic", "myTestStringProducer");
    return template;
  }

}
