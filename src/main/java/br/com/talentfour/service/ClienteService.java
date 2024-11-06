package br.com.talentfour.service;

import br.com.talentfour.controller.dto.request.ClienteRequestToFilterDto;
import br.com.talentfour.controller.dto.request.ClienteRequestToSaveDto;
import br.com.talentfour.controller.dto.response.ClienteResponseSavedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClienteService {

    ClienteResponseSavedDto save(ClienteRequestToSaveDto request);

    ClienteResponseSavedDto findById(Long id);

    Page<ClienteResponseSavedDto> filter(ClienteRequestToFilterDto filterDto, Pageable pageable);

    void deleteById(Long id);
}
