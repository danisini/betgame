package com.interview.betgame.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.util.List;
import java.util.Stack;

public class StringStackConverter implements AttributeConverter<Stack<Short>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public StringStackConverter() {
    }

    @Override
    public String convertToDatabaseColumn(Stack<Short> shorts) {
        if (shorts == null) {
            return null;
        } else {
            try {
                return objectMapper.writeValueAsString(shorts);
            } catch (JsonProcessingException var3) {
                throw new RuntimeException("failed to serialize attribute to json string", var3);
            }
        }
    }

    @Override
    public Stack<Short> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        } else {
            try {
                return objectMapper.readValue(s, new TypeReference<Stack<Short>>(){});
            } catch (JsonProcessingException var3) {
                throw new RuntimeException(String.format("failed to deserialize from json string %s to attribute", s), var3);
            }
        }
    }

}
