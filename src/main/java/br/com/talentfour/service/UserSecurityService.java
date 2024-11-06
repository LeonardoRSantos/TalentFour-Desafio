package br.com.talentfour.service;

import br.com.talentfour.controller.dto.request.AuthenticationRequestDto;
import br.com.talentfour.controller.dto.response.AuthenticationResponseDto;
import br.com.talentfour.domain.entity.UserSystem;
import br.com.talentfour.exception.ServiceBusinessException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserSecurityService {
    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) throws ServiceBusinessException, NoSuchAlgorithmException, InvalidKeySpecException;

    void changePassword(UserSystem user, String newPassword) throws ServiceBusinessException, NoSuchAlgorithmException, InvalidKeySpecException;

}
