package com.Deekshith.Project.UserService.KafkaConfig;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.List;

public class ArrayDeserializer implements Deserializer<int[]> {
    @Override
    public int[] deserialize(String s, byte[] bytes) {

        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.readValue(bytes , int[].class);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
