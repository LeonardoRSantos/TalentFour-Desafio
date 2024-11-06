package br.com.talentfour.controller.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestToFilterDto {

    private Long id;
    private String name;
    private String email;
    private String state;
}
