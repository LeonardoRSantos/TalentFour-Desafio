package br.com.talentfour.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserStatusType {
    ACTIVE(1, "Ativo"),
    INACTIVE(2, "Inativo"),
    SUSPENDED(3, "Suspenso");

    private final Integer code;
    private final String description;

    UserStatusType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static UserStatusType findByCode(Integer code) {
        return Arrays.stream(UserStatusType.values())
                .filter(st -> st.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}

