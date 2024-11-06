package br.com.talentfour.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    private String street;
    private String city;
    private String state;

}
