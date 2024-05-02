package com.bhuvanesh.blog.dynamickafka.producer;

import com.amazonaws.services.schemaregistry.serializers.avro.AWSKafkaAvroSerializer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import com.amazonaws.services.schemaregistry.utils.AvroRecordType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;
import software.amazon.msk.auth.iam.IAMClientCallbackHandler;
import software.amazon.msk.auth.iam.IAMLoginModule;

@Component
@RequiredArgsConstructor
public class KafkaProducerManager {
  @Value("${aws.kafka.bootstrap-servers}")
  private String bootstrapServers;

  private final GenericApplicationContext context;
  private final Map<Pattern, ProducerFactory<Object, Object>> map = new LinkedHashMap<>();
  
  public DefaultKafkaProducerFactory<Object,Object> createProducer() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");

    return new DefaultKafkaProducerFactory<>(props);
  }
  
  public RoutingKafkaTemplate routingTemplate(String topicPattern, String beanName) {
    DefaultKafkaProducerFactory<Object,Object> producerFactory = createProducer();
    context.registerBean(beanName, DefaultKafkaProducerFactory.class, () -> producerFactory);

    map.put(Pattern.compile(topicPattern), producerFactory);
    return new RoutingKafkaTemplate(map);
  }
  
  private Map<String, Object> getKafkaSSlConfig() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(SaslConfigs.SASL_MECHANISM, IAMLoginModule.MECHANISM);
    properties.put(SaslConfigs.SASL_CLIENT_CALLBACK_HANDLER_CLASS,
        IAMClientCallbackHandler.class);
    properties.put(SaslConfigs.SASL_JAAS_CONFIG,
        "software.amazon.msk.auth.iam.IAMLoginModule required;");
    properties.put("security.protocol", SecurityProtocol.SASL_SSL.name);
    return properties;
  }
}
