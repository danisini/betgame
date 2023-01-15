package com.interview.betgame.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.util.List;

public class StringListConverter implements AttributeConverter<List<Short>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public StringListConverter() {
    }

    @Override
    public String convertToDatabaseColumn(List<Short> shorts) {
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
    public List<Short> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        } else {
            try {
                return objectMapper.readValue(s, new TypeReference<List<Short>>(){});
            } catch (JsonProcessingException var3) {
                throw new RuntimeException(String.format("failed to deserialize from json string %s to attribute", s), var3);
            }
        }
    }

}
