package br.com.talentfour.domain.entity;

import br.com.talentfour.domain.enums.UserStatusType;
import br.com.talentfour.domain.enums.converter.UserStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSystem extends Person {

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    @Convert(converter = UserStatusConverter.class)
    private UserStatusType status;

    @Column(length = 255)
    private String hash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> profiles = new ArrayList<>();
}
