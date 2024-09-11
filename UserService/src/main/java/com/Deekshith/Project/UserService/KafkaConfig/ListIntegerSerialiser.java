package com.Deekshith.Project.UserService.KafkaConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class ListIntegerSerialiser implements Serializer<List<Integer>> {
    @Override
    public byte[] serialize(String topic, List<Integer> postIds) {
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            return  objectMapper.writeValueAsBytes(postIds);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
