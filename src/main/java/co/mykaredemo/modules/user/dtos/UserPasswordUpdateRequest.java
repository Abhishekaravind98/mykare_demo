package co.mykaredemo.modules.user.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordUpdateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String newPassword;
    @NotNull
    private String confirmNewPassword;

}
