package br.com.talentfour.controller;

import br.com.talentfour.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static br.com.talentfour.fixture.TestUtils.getContentFromInputStream;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WithMockUser
@AutoConfigureMockMvc
public class ClienteControllerTest extends AbstractIntegrationTest {

    private static final String BASE_URL = "/v1/cliente";

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("when request to save with valid client")
    @Test
    @WithMockUser
    void shouldSavedClientWhenValid() throws Exception {
        var validClienteBody = getContentFromInputStream(
                this.getClass().getClassLoader().getResourceAsStream("samples/request/client/when-request-to-save-with-valid-client.json")
        );
        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validClienteBody));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @DisplayName("when request to update with valid client")
    @Test
    @WithMockUser
    @Sql({"/db/address/insert_address.sql", "/db/client/insert_client.sql"})
    void shouldUpdateClientWhenValid() throws Exception {
        var validClienteBody = getContentFromInputStream(
                this.getClass().getClassLoader().getResourceAsStream("samples/request/client/when-request-to-update-with-valid-client.json")
        );

        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validClienteBody));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@example.com"))
                .andExpect(jsonPath("$.address.street").value("Rua das Flores, 123"))
                .andExpect(jsonPath("$.address.city").value("São Paulo"))
                .andExpect(jsonPath("$.address.state").value("SP"));
    }

    @DisplayName("when request to update with name client null")
    @Test
    @WithMockUser
    void shouldSavedClientNameWhenInvalid() throws Exception {
        var validClienteBody = getContentFromInputStream(
                this.getClass().getClassLoader().getResourceAsStream("samples/request/client/when-request-to-saved-with-invalid-client.json")
        );

        ResultActions response = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validClienteBody));

        response.andExpect(status().isPreconditionFailed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("O nome não pode ser nula ou vazia."));
    }

    @DisplayName("When request to filter client request name")
    @Test
    @WithMockUser
    @Sql({"/db/address/insert_address.sql", "/db/client/insert_client.sql"})
    void shouldFilteredClientNameWhenValid() throws Exception {
        String url = BASE_URL + "?name=Leonardo";

        ResultActions response = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("leonardo"));
    }

    @DisplayName("When request to filter client request name")
    @Test
    @WithMockUser
    @Sql({"/db/address/insert_address.sql", "/db/client/insert_client.sql"})
    void shouldFilteredClientStateWhenValid() throws Exception {
        String url = BASE_URL + "?state=Maranhão";

        ResultActions response = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].address.state").value("Maranhão"));

    }

    @DisplayName("When request to delete with valid client")
    @Test
    @WithMockUser
    @Sql({"/db/address/insert_address.sql", "/db/client/insert_client.sql"})
    void shouldDeletedClientWhenValid() throws Exception {
        long validId = 1;

        ResultActions response = mockMvc.perform(delete(BASE_URL + "/" + validId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @DisplayName("When request to delete with invalid client")
    @Test
    @WithMockUser
    @Sql({"/db/address/insert_address.sql", "/db/client/insert_client.sql"})
    void shouldDeletedClientWhenInvalid() throws Exception {
        long validId = 9999;

        ResultActions response = mockMvc.perform(delete(BASE_URL + "/" + validId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isPreconditionFailed())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Não pode excluir, cliente não encontrado com o ID 9999"));
    }

    @DisplayName("When request to delete with invalid client null")
    @Test
    @WithMockUser
    @Sql({"/db/address/insert_address.sql", "/db/client/insert_client.sql"})
    void shouldDeletedClientWhenNull() throws Exception {

        ResultActions response = mockMvc.perform(delete(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}
