package org.showtime.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.showtime.domain.Seat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Converter
public class SeatListConverter implements AttributeConverter<List<Seat>, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Seat> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // In a real application, you'd want to handle this exception more gracefully
            throw new IllegalArgumentException("Error converting list of seats to JSON", e);
        }
    }

    @Override
    public List<Seat> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Seat>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to list of seats", e);
        }
    }
}
