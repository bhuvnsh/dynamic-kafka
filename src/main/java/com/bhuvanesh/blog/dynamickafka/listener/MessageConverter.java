package com.bhuvanesh.blog.dynamickafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.SmartMessageConverter;


@Slf4j
public class MessageConverter implements SmartMessageConverter {

  @Override
  public Object fromMessage(Message<?> message, Class<?> targetClass, Object conversionHint) {
    log.info("from message converter with conversion hint, message:{}", message);
    return message.getPayload();
  }

  @Override
  public Message<?> toMessage(Object payload, MessageHeaders headers, Object conversionHint) {
    log.info("to message converter with conversion hint, payload:{}", payload);
    return null;
  }


  @Override
  public Object fromMessage(Message<?> message, Class<?> targetClass) {
    log.info("from message converter, message:{}", message);
    return message.getPayload();
  }

  @Override
  public Message<?> toMessage(Object payload, MessageHeaders headers) {
    log.info("to message converter, payload:{}", payload);
    return null;
  }
}
