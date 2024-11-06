package br.com.talentfour.controller.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {
    private String username;
    private String password;
}