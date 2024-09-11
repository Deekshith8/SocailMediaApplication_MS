package com.Deekshith.Project.UserService.KafkaConfig;

import com.Deekshith.Project.UserService.dto.Posts;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.List;

public class ListOfPostsDeSerialiser implements Deserializer<List<Posts>> {


    @Override
    public List<Posts> deserialize(String s, byte[] bytes) {
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
