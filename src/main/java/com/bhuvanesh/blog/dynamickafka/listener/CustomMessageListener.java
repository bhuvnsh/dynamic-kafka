//package com.bhuvanesh.blog.dynamickafka.listener;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.listener.MessageListener;
//
//@Slf4j
//@RequiredArgsConstructor
//public class CustomMessageListener<K, V> implements MessageListener<K, V> {
//
//  @Override
//  public void onMessage(ConsumerRecord<K, V> record) {
//    log.info("RECORD PROCESSING: {}", record.value());
//  }
//}
