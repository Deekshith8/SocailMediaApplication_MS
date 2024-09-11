package com.Deekshith.Project.PostService.KafkaConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class ArraySerializer implements Serializer<int[]> {

    @Override
    public byte[] serialize(String s, int[] postIdAndUserId) {
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            return  objectMapper.writeValueAsBytes(postIdAndUserId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
