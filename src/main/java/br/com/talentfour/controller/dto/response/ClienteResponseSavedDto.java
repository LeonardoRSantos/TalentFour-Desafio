package br.com.talentfour.controller.dto.response;

import br.com.talentfour.controller.dto.request.AddressRequestToSaveDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteResponseSavedDto {

    private Long id;
    private String name;
    private String email;
    private AddressRequestToSaveDto address;
}
