package com.bhuvanesh.blog.dynamickafka.listener;

import lombok.Builder;
import lombok.Data;
import org.springframework.kafka.listener.MessageListener;

@Data
@Builder
public class ListenerKafkaProperties {

  private final String groupId;
  private final String listenerId;
  private final String topic;
  private final String partition;
  private final MessageListener messageListener;
}
