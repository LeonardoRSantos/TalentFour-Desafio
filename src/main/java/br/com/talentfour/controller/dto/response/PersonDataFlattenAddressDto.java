//package br.com.talentfour.controller.dto.response;
//
//import br.com.talentfour.controller.dto.request.validators.ClienteGroupSequenceProvider;
//import br.com.talentfour.domain.enums.PersonType;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.validator.group.GroupSequenceProvider;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
//public class PersonDataFlattenAddressDto {
//
//    private Long id;
//
//    private PersonType personType;
//
//    private String documentNumber;
//
//    private String name;
//
//    private LocalDate birthDate;
//
//    private List<ContactResponseSavedDto> contacts;
//
//    private LocalDateTime createdAt;
//
//}
