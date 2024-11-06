package br.com.talentfour.mapper;

import br.com.talentfour.controller.dto.request.ClienteRequestToSaveDto;
import br.com.talentfour.controller.dto.response.ClienteResponseSavedDto;
import br.com.talentfour.domain.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    Cliente toEntity(ClienteRequestToSaveDto requestDto);

    ClienteResponseSavedDto toResponseDto(Cliente entity);

    default Page<ClienteResponseSavedDto> toResponseDto(Page<Cliente> clientsPage) {
        return clientsPage.map(this::toResponseDto);
    }
}
