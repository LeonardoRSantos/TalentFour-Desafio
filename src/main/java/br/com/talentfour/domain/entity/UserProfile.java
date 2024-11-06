package br.com.talentfour.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;

import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_seq")
    @SequenceGenerator(name = "user_profile_seq", sequenceName = "user_profile_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserSystem userSystem;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
