package br.com.talentfour.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class AddressRequestToSaveDto {

   private Long id;

   @NotBlank
   private String street;

   @NotBlank
   private String city;

   @NotBlank
   private String state;

}