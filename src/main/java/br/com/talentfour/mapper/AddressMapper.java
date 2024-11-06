package br.com.talentfour.mapper;

import br.com.talentfour.controller.dto.request.AddressRequestToSaveDto;
import br.com.talentfour.controller.dto.response.AddressResponseSavedDto;
import br.com.talentfour.domain.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    Address toEntity(AddressRequestToSaveDto addressRequestDto);

    AddressResponseSavedDto toResponseDto(Address address);

    List<AddressResponseSavedDto> toResponseDto(List<Address> addresses);

    default Page<AddressResponseSavedDto> toResponseDto(Page<Address> addressPage) {
        return addressPage.map(this::toResponseDto);
    }

}
