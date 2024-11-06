package br.com.talentfour.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ClienteRequestToSaveDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @Valid
    private AddressRequestToSaveDto address;

}
