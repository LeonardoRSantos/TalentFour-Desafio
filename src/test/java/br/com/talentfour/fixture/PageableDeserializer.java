package br.com.talentfour.fixture;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public class PageableDeserializer extends JsonDeserializer<Pageable> {

    @Override
    public Pageable deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = (JsonNode) p.getCodec().readTree(p);

        int pageNumber = node.get("pageNumber").asInt();
        int pageSize = node.get("pageSize").asInt();
        Sort sort = Sort.unsorted();

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}