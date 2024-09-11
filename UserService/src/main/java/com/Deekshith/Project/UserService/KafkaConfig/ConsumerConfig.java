package com.Deekshith.Project.UserService.KafkaConfig;


import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfig {

    @Bean
    public Map<String , Object> consumerConfigsForSecurityService(){

        Map<String, Object> cons = new HashMap<>();
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , StringDeserializer.class);
//        cons.put(JsonDeserializer.TRUSTED_PACKAGES , "com.example.kafkaPractise.Entity");

        //cons.put(ConsumerConfig.GROUP_ID_CONFIG , "userConsumer");
        return  cons;
    }

    @Bean(value = "consumerFactoryForSecurityService")
    @Qualifier(value = "consumerFactoryForSecurityService")
    public DefaultKafkaConsumerFactory consumerFactoryForSecurityService(){
        return  new DefaultKafkaConsumerFactory<>(consumerConfigsForSecurityService());
    }

    @Bean(value = "kafkaListenerContainerForSecurityService")
    @Qualifier(value = "kafkaListenerContainerForSecurityService")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String , Object>> kafkaListenerContainerForSecurityService(){
        ConcurrentKafkaListenerContainerFactory<String,Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForSecurityService());
        return factory;
    }


    /// for PostService Listeners

    @Bean
    public Map<String , Object> consumerConfigsForPostService(){

        Map<String, Object> cons = new HashMap<>();
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        cons.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , ArrayDeserializer.class);
//        cons.put(JsonDeserializer.TRUSTED_PACKAGES , "com.example.kafkaPractise.Entity");

        //cons.put(ConsumerConfig.GROUP_ID_CONFIG , "userConsumer");
        return  cons;
    }

    @Bean(value = "consumerFactoryForPostService")
    @Primary
    @Qualifier(value = "consumerFactoryForPostService")
    public DefaultKafkaConsumerFactory consumerFactoryForPostService(){
        return  new DefaultKafkaConsumerFactory<>(consumerConfigsForPostService());
    }

    @Bean(value = "kafkaListenerContainerForPostService")
    @Primary
    @Qualifier(value = "kafkaListenerContainerForPostService")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String , Object>> kafkaListenerContainerForPostService(){
        ConcurrentKafkaListenerContainerFactory<String,Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForPostService());
        return factory;
    }


}
