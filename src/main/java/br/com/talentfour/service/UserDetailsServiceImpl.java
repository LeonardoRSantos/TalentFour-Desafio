package br.com.talentfour.service;

import br.com.talentfour.domain.repository.UserSystemRepository;
import br.com.talentfour.mapper.UserDetailViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserSystemRepository userSystemRepository;
    private final UserDetailViewMapper userDetailViewMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailViewMapper.toView(userSystemRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
    }

}

