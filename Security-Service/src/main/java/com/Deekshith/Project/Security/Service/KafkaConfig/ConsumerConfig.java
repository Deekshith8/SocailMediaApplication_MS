package com.Deekshith.Project.Security.Service.KafkaConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ConsumerConfig {

    @Bean
    public Map<String , Object> consumerConfigs(){

        Map<String, Object> cons = new HashMap<>();
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , StringDeserializer.class);
//        cons.put(JsonDeserializer.TRUSTED_PACKAGES , "com.example.kafkaPractise.Entity");

        //cons.put(ConsumerConfig.GROUP_ID_CONFIG , "userConsumer");
        return  cons;
    }

    @Bean
    public DefaultKafkaConsumerFactory consumerFactory(){
        return  new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String , Object>> kafkaListenerContainer(){
        ConcurrentKafkaListenerContainerFactory<String,Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
