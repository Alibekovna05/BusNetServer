package kz.busnet.busnetserver.email;

import lombok.Getter;

@Getter
public enum SimpleTemplate {

    SUCCESSFUL_REGISTRATION("successful_registration");
    private final String name;

    SimpleTemplate(String name) {
        this.name = name;
    }
}
