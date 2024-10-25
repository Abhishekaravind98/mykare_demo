package co.mykaredemo.modules.user.ports.api;

import co.mykaredemo.dtos.PaginatedList;
import co.mykaredemo.modules.user.dtos.UserCreateRequest;
import co.mykaredemo.modules.user.dtos.UserPasswordUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserView;
import co.mykaredemo.modules.user.misc.UserFilter;

import java.util.List;

public interface UserServicePort {
    UserView create(UserCreateRequest request);

    UserView get(Long id);

    UserView delete(Long id);

    UserView update(Long id, UserUpdateRequest request);

    UserView resetPassword(UserPasswordUpdateRequest request);

    PaginatedList<UserView> getAllPaginated(UserFilter filter);

    List<UserView> getAll(UserFilter filter);

    UserView findByEmailId(String emailId);

    UserView blockUser(Long id);

    UserView unBlockUser(Long id);
}
