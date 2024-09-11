package com.Deekshith.Project.PostService.KafkaConfig;


import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("PostService" , 2, (short) 1);
    }

    @Bean
    public Map<String,Object> producerConfigs(){
        Map<String,Object>  prodcuerconfigs = new HashMap<>();
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , ArraySerializer.class);

        return  prodcuerconfigs;
    }



    @Bean
    public ProducerFactory<String ,Object> producerFactory(){
        return  new DefaultKafkaProducerFactory(producerConfigs());
    }



    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }


}
