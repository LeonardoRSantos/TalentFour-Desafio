package br.com.talentfour.controller;

import br.com.talentfour.controller.dto.request.AuthenticationRequestDto;
import br.com.talentfour.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/security")
@Slf4j
public class UserSecurityController {

    private final UserSecurityService userSecurityService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws Exception {
        return ResponseEntity.ok(userSecurityService.login(authenticationRequestDto));
    }
}
