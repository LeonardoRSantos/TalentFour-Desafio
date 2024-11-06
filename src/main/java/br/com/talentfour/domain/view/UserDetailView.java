package br.com.talentfour.domain.view;

import br.com.talentfour.domain.enums.UserStatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserDetailView implements UserDetails {
    private String name;
    private String username;
    private UserStatusType status;
    @JsonIgnore
    private String hash;
    private String initialPage;
    private List<String> profiles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profiles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatusType.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatusType.ACTIVE;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", name, username);
    }
}
