package co.mykaredemo.modules.user.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    @NotNull
    private String name;
    @NotNull
    private String emailId;
    private String phoneNumber;

}
