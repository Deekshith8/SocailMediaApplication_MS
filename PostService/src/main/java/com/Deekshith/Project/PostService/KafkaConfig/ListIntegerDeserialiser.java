package com.Deekshith.Project.PostService.KafkaConfig;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.List;

public class ListIntegerDeserialiser implements Deserializer<List<Integer>> {
    @Override
    public List<Integer> deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
           return objectMapper.readValue(bytes , List.class);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
