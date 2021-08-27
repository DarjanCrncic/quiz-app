package com.darjan.quizapp.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class Base64Deserializer extends JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
       
    	String value = parser.getValueAsString();
		byte[] decoded = Base64.decodeBase64(value);
		value = new String(decoded, StandardCharsets.UTF_8);
		return value;
    }
}