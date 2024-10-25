package co.mykaredemo.modules.auth.dtos;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private Long userId;
    private String name;
    private String emailId;
    private String phoneNumber;
    private String role;
    private String token;
    private Date expiry;

}
