package br.com.talentfour.domain.enums.converter;

import br.com.talentfour.domain.enums.UserStatusType;

public class UserStatusConverter extends GenericConverter<UserStatusType, Integer> {
    public UserStatusConverter() {super("code");
    }
}
