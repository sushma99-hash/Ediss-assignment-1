package cmu.edu.ds.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ISBNDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        // Check for both "ISBN" and "isbn" in the JSON
        if (node.has("ISBN")) {
            return node.get("ISBN").asText();
        } else if (node.has("isbn")) {
            return node.get("isbn").asText();
        }
        // If neither is present, return null (or throw an exception)
        return null;
    }
}