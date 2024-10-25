package co.mykaredemo.modules.auth.dtos;

import co.mykaredemo.modules.auth.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    private Long userId;

    private String token;

    private String userName;

    private Role userRole;

    public String getName() {
        return userName;
    }

    public UserSession(Long userId, String token, String userName, Role userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
    }
}
