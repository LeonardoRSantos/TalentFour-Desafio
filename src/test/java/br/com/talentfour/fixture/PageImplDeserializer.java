package br.com.talentfour.fixture;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageImplDeserializer<T> extends JsonDeserializer<PageImpl<T>> {

    private final Class<T> contentClass;

    public PageImplDeserializer(Class<T> contentClass) {
        this.contentClass = contentClass;
    }

    @Override
    public PageImpl<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        // Desserializando a lista de conteúdo
        List<T> content = new ArrayList<>();
        JsonNode contentNode = node.get("content");
        if (contentNode.isArray()) {
            for (JsonNode itemNode : contentNode) {
                T item = p.getCodec().treeToValue(itemNode, contentClass);
                content.add(item);
            }
        }

        // Desserializando o Pageable
        JsonNode pageableNode = node.get("pageable");
        int pageNumber = pageableNode.get("pageNumber").asInt();
        int pageSize = pageableNode.get("pageSize").asInt();
        Sort sort = Sort.unsorted(); // Se necessário, deserializar o Sort aqui

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Outros campos que não são utilizados diretamente
        boolean last = node.get("last").asBoolean();
        boolean first = node.get("first").asBoolean();
        int totalElements = node.get("totalElements").asInt();
        int totalPages = node.get("totalPages").asInt();
        int number = node.get("number").asInt();

        return new PageImpl<>(content, pageable, totalElements);
    }
}
