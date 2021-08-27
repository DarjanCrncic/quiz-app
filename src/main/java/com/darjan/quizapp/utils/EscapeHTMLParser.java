package com.darjan.quizapp.utils;

import java.io.IOException;

import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class EscapeHTMLParser extends JsonDeserializer<Object> {

	@Override
	public Object deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String value = parser.getValueAsString();
		value = HtmlUtils.htmlEscape(value);
		return value;

	}
}
