package br.com.talentfour.controller;

import br.com.talentfour.controller.dto.request.ClienteRequestToFilterDto;
import br.com.talentfour.controller.dto.request.ClienteRequestToSaveDto;
import br.com.talentfour.controller.dto.response.ClienteResponseSavedDto;
import br.com.talentfour.controller.dto.response.DeleteResponseDto;
import br.com.talentfour.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Objects;

@RestController
@RequestMapping("/v1/cliente")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponseSavedDto> save(@RequestBody ClienteRequestToSaveDto request) {
        var response = service.save(request);
        return Objects.nonNull(request.getId()) ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseSavedDto>> findAll(ClienteRequestToFilterDto request, Pageable pageable) {
        return ResponseEntity.ok(service.filter(request, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponseDto> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok(new DeleteResponseDto("O cliente foi excluído com sucesso!"));
    }
}
