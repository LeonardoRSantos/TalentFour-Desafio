package br.com.talentfour.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.ManyToMany;

import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "system_name", nullable = false, length = 10)
    private String systemName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "profiles")
    private Set<UserSystem> userSystems;

}
