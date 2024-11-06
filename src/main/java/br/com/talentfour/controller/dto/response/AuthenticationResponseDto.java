package br.com.talentfour.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(force = true)
@Getter
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String token;
}
