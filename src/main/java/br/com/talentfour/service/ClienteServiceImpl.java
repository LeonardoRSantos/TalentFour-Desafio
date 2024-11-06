package br.com.talentfour.service;

import br.com.talentfour.controller.dto.request.ClienteRequestToFilterDto;
import br.com.talentfour.controller.dto.request.ClienteRequestToSaveDto;
import br.com.talentfour.controller.dto.response.ClienteResponseSavedDto;
import br.com.talentfour.domain.entity.Address;
import br.com.talentfour.domain.entity.Cliente;
import br.com.talentfour.domain.repository.AddressRepository;
import br.com.talentfour.domain.repository.ClienteRepository;
import br.com.talentfour.exception.ServiceBusinessException;
import br.com.talentfour.mapper.AddressMapper;
import br.com.talentfour.mapper.ClienteMapper;
import br.com.talentfour.util.SecurityAppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ClienteMapper clienteMapper;


    @Override
    @Transactional
    public ClienteResponseSavedDto save(ClienteRequestToSaveDto request) {
        Cliente cliente = validateToSave(request);
        Cliente saved = repository.save(cliente);
        return clienteMapper.toResponseDto(saved);
    }


    @Override
    public ClienteResponseSavedDto findById(Long id) {
        validateId(id);

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ServiceBusinessException("Cliente não encontrado com o ID " + id));

        return mapper.toResponseDto(cliente);
    }

    /**
     * @param filter
     * @param pageable Relizei essa implementação com JPA utilizando as boas práticas de pageable.
     */
//    @Override
//    @Transactional(readOnly = true)
//    public Page<ClienteResponseSavedDto> filter(ClienteRequestToFilterDto filter, Pageable pageable) {
//        return repository.findAll(ClienteSpecs.filter(filter),
//                pageable).map(mapper::toResponseDto);
//    }
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponseSavedDto> filter(ClienteRequestToFilterDto filter, Pageable pageable) {
        return repository.filter(filter.getName(),
                        filter.getEmail(),
                        filter.getState(),
                        pageable)
                .map(mapper::toResponseDto);
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        validateToDelete(id);

        repository.deleteById(id);
    }

    private void validateToDelete(Long id) {
        if (id == null) {
            throw new ServiceBusinessException("O ID deve ser informado para exclusão.");
        }

        if (!repository.existsById(id)) {
            throw new ServiceBusinessException("Não pode excluir, cliente não encontrado com o ID " + id);
        }
    }


    private Cliente validateToSave(ClienteRequestToSaveDto dto) {

        if (dto.getName() == null || StringUtils.isEmpty(dto.getName())) {
            throw new ServiceBusinessException("O nome não pode ser nula ou vazia.");
        }

        if (dto.getEmail() == null || StringUtils.isEmpty(dto.getEmail())) {
            throw new ServiceBusinessException("O email não pode ser nula ou vazia.");
        }

        Address address = null;
        if (Objects.nonNull(dto.getAddress()) && Objects.nonNull(dto.getAddress().getId())) {

            address = addressRepository.findById(dto.getAddress().getId())
                    .orElseThrow(() -> new ServiceBusinessException("Endereço não encontrado para o ID informado."));

            address.setStreet(dto.getAddress().getStreet());
            address.setCity(dto.getAddress().getCity());
            address.setState(dto.getAddress().getState());

        } else {

            address = addressMapper.toEntity(dto.getAddress());
        }

        if (Objects.nonNull(dto.getId())) {
            if (!repository.existsById(dto.getId())) {
                throw new ServiceBusinessException("O id do Cliente informado não existe para ser atualizado!.");
            }
        }


        var cliente = mapper.toEntity(dto);

        cliente.setUsername(SecurityAppUtil.getAuthenticatedUsername());
        cliente.setChangeDate(new Date());

        if (address != null) {
            cliente.setAddress(address);
        }

        return cliente;
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new ServiceBusinessException("A identificação do Cliente é obrigatória para a pesquisa.");
        }
    }
}
