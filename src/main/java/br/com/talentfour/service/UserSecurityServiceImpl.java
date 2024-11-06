package br.com.talentfour.service;

import br.com.talentfour.controller.dto.request.AuthenticationRequestDto;
import br.com.talentfour.controller.dto.response.AuthenticationResponseDto;
import br.com.talentfour.domain.entity.UserSystem;
import br.com.talentfour.domain.repository.UserSystemRepository;
import br.com.talentfour.exception.ServiceBusinessException;
import br.com.talentfour.exception.UnauthorizedException;
import br.com.talentfour.mapper.UserDetailViewMapper;
import br.com.talentfour.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserSystemRepository userSystemRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailViewMapper userDetailViewMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto)
            throws ServiceBusinessException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.getUsername(),
                            authenticationRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Acesso não autorizado para os dados informados", e);
        }

        final UserDetails userDetails = userDetailViewMapper.toView(
                userSystemRepository.findByUsername(authenticationRequestDto.getUsername())
                        .orElseThrow(() -> new UnauthorizedException("Acesso não autorizado para os dados informados"))
        );

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponseDto(jwt);
    }

    public void changePassword(UserSystem user, String newPassword) throws ServiceBusinessException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserSystem existingUser = validateToChangePassword(user);
        existingUser.setHash(passwordEncoder.encode(newPassword));
        userSystemRepository.save(existingUser);
    }

    private UserSystem validateToChangePassword(UserSystem user) {
        return userSystemRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ServiceBusinessException(
                        String.format("Usuário [%s] com nome [%s] não encontrado para alteração de senha.",
                                user.getUsername(), user.getName())
                ));
    }

}

