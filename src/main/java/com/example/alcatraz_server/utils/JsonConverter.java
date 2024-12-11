package com.example.alcatraz_server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> T fromJson(String json, Class<T> toClass) throws JsonProcessingException {
        return objectMapper.readValue(json, toClass);
    }
}
