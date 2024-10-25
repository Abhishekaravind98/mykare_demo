package co.mykaredemo.modules.user.mapper;

import co.mykaredemo.annotation.IgnoreBaseAudit;
import co.mykaredemo.modules.user.dtos.UserBasicView;
import co.mykaredemo.modules.user.dtos.UserCreateRequest;
import co.mykaredemo.modules.user.dtos.UserUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserView;
import co.mykaredemo.modules.user.entities.User;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    @IgnoreBaseAudit
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "USER")
    User map(UserCreateRequest request);

    UserView getView(User user);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(target = "password", source = "password")
    User updatePassword(User user, String password);

    default Page<UserView> getPaginatedView(Page<User> users) {
        if (users == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        return users.map(this::getView);
    }

    List<UserView> getView(List<User> users);

    UserBasicView map(User user);
}
