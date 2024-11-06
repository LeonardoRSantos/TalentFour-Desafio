package br.com.talentfour.mapper;

import br.com.talentfour.domain.entity.Profile;
import br.com.talentfour.domain.entity.UserSystem;
import br.com.talentfour.domain.view.UserDetailView;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDetailViewMapper {

    UserDetailView toView(UserSystem userSystem);

    default List<String> mapProfilesToNames(List<Profile> profiles) {
        return profiles.stream()
                .map(Profile::getName).toList();
    }
}
