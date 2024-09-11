package com.Deekshith.Project.UserService.KafkaConfig;

import org.apache.kafka.clients.admin.NewTopic;
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
        return new NewTopic("UserService" , 2, (short) 1);
    }

    @Bean
    public Map<String,Object> producerConfigs(){
        Map<String,Object>  prodcuerconfigs = new HashMap<>();
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , ListIntegerSerialiser.class);

        return  prodcuerconfigs;
    }



    @Bean
    public ProducerFactory<String ,Object> producerFactory(){
        return  new DefaultKafkaProducerFactory(producerConfigs());
    }



    @Bean
    public KafkaTemplate<String,Object> kafkaTemplateForPostService(){
        return new KafkaTemplate<>(producerFactory());
    }

    //
    @Bean
    public Map<String,Object> producerConfigsForUserCredentials(){
        Map<String,Object>  prodcuerconfigs = new HashMap<>();
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prodcuerconfigs.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , StringSerializer.class);

        return  prodcuerconfigs;
    }



    @Bean
    public ProducerFactory<String ,Object> producerFactoryForUserCredentials(){
        return  new DefaultKafkaProducerFactory(producerConfigsForUserCredentials());
    }



    @Bean
    public KafkaTemplate<String,Object> kafkaTemplateForPostServiceForUserCredentials(){
        return new KafkaTemplate<>(producerFactoryForUserCredentials());
    }


}
