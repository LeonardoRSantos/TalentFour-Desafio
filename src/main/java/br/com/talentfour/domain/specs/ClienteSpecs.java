package br.com.talentfour.domain.specs;

import br.com.talentfour.controller.dto.request.ClienteRequestToFilterDto;
import br.com.talentfour.domain.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

public class ClienteSpecs {

    public static Specification<Cliente> filter(ClienteRequestToFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if(Objects.nonNull(filter.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if(Objects.nonNull(filter.getEmail())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (Objects.nonNull(filter.getState())){
                predicates.add(criteriaBuilder.equal(root.get("address").get("state"), filter.getState()));
            }

            query.orderBy(criteriaBuilder.asc(root.get("name")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
