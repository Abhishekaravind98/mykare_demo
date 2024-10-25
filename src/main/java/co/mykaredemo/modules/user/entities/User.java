package co.mykaredemo.modules.user.entities;

import co.mykaredemo.constants.DbConstants;
import co.mykaredemo.dtos.AbstractTransactionalEntity;
import co.mykaredemo.modules.auth.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = DbConstants.USER)
public class User extends AbstractTransactionalEntity {
    private String name;
    private String emailId;
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
