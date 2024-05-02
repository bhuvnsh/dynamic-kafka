package com.bhuvanesh.blog.dynamickafka.listener;

import com.amazonaws.services.schemaregistry.deserializers.avro.AWSKafkaAvroDeserializer;
import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import com.amazonaws.services.schemaregistry.utils.AvroRecordType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.converter.SmartMessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;
import software.amazon.msk.auth.iam.IAMClientCallbackHandler;
import software.amazon.msk.auth.iam.IAMLoginModule;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerContainerManager<K, V> {
  @Value("${aws.kafka.bootstrap-servers}")
  private String bootstrapServers;
  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> kafkaListenerContainerFactory;

  @SneakyThrows
  public KafkaListenerEndpoint createKafkaListenerEndpoint(ListenerKafkaProperties properties) {
    MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
    kafkaListenerEndpoint.setId(properties.getListenerId());
    kafkaListenerEndpoint.setGroupId(properties.getGroupId());
    kafkaListenerEndpoint.setAutoStartup(true);
    kafkaListenerEndpoint.setTopics(properties.getTopic());
    kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
    kafkaListenerEndpoint.setConsumerProperties(buildConsumerProperties());
    kafkaListenerEndpoint.setMessagingConverter(messageConverter());
    kafkaListenerEndpoint.setBean(properties.getMessageListener());
    kafkaListenerEndpoint.setMethod(properties.getMessageListener().getClass().getMethod("onMessage", ConsumerRecord.class));

    return kafkaListenerEndpoint;
  }

  private SmartMessageConverter messageConverter() {
    return new MessageConverter();
  }

  private Properties buildConsumerProperties() {
    Properties consumerProperties = new Properties();
    consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        ErrorHandlingDeserializer.class.getName());
    consumerProperties.setProperty(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS,
        JsonDeserializer.class.getName());
    consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        ErrorHandlingDeserializer.class.getName());
    consumerProperties.setProperty(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
        JsonDeserializer.class.getName());

    return consumerProperties;
  }

  @SneakyThrows
  public void registerListener(ListenerKafkaProperties properties, boolean startImmediately) {
    kafkaListenerEndpointRegistry.registerListenerContainer(
        createKafkaListenerEndpoint(properties), kafkaListenerContainerFactory, startImmediately
    );
  }

}