package br.com.talentfour.controller.dto.response;

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
public class AddressResponseSavedDto {

    private Long id;
    private String street;
    private String city;
    private String state;

}
