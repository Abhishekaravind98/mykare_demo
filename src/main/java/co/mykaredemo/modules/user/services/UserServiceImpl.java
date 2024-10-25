package co.mykaredemo.modules.user.services;

import co.mykaredemo.dtos.PaginatedList;
import co.mykaredemo.enums.CommonErrorCodeEnum;
import co.mykaredemo.enums.EntityStatus;
import co.mykaredemo.exceptions.ServiceException;
import co.mykaredemo.modules.user.dtos.UserCreateRequest;
import co.mykaredemo.modules.user.dtos.UserPasswordUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserView;
import co.mykaredemo.modules.user.entities.User;
import co.mykaredemo.modules.user.mapper.UserMapper;
import co.mykaredemo.modules.user.misc.UserFilter;
import co.mykaredemo.modules.user.ports.api.UserServicePort;
import co.mykaredemo.modules.user.ports.spi.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {
    private final UserPersistencePort userJpaAdapter;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserView create(UserCreateRequest request) {
        User exist = userJpaAdapter.findByEmailIdAndStatus(request.getEmailId());
        if (exist != null && exist.getStatus().equals(EntityStatus.ACTIVE)) {
            log.error("Email already exist: {}", exist.getEmailId());
            throw new ServiceException(CommonErrorCodeEnum.BAD_REQUEST);
        }
        User user = mapper.map(request);
        user.setPassword(passwordEncoder.encode("user@123"));
        userJpaAdapter.save(user);
        return mapper.getView(user);
    }

    @Override
    public UserView get(Long id) {
        User user = userJpaAdapter.findById(id);
        if (user == null) {
            log.error("No user found :{}", id);
            throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        }
        return mapper.getView(user);
    }

    @Override
    public UserView delete(Long id) {
        User user = userJpaAdapter.findById(id);
        if (user == null) {
            log.error("No user found :{}", id);
            throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        }
        user.setStatus(EntityStatus.DELETED);
        userJpaAdapter.save(user);
        return mapper.getView(user);
    }

    @Override
    public UserView update(Long id, UserUpdateRequest request) {
        User user = userJpaAdapter.findById(id);
        if (user == null) {
            log.error("No user found :{}", id);
            throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        }
        mapper.update(user, request);
        userJpaAdapter.save(user);
        return mapper.getView(user);
    }

    @Override
    public UserView resetPassword(UserPasswordUpdateRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new ServiceException(CommonErrorCodeEnum.BAD_REQUEST);
        }
        User user = userJpaAdapter.findById(request.getUserId());
        if (user == null) {
            log.error("No user found :{}", request.getUserId());
            throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        }
        String password = passwordEncoder.encode(request.getNewPassword());
        userJpaAdapter.save(mapper.updatePassword(user, password));
        return mapper.getView(user);
    }

    @Override
    public PaginatedList<UserView> getAllPaginated(UserFilter filter) {
        Page<User> page = userJpaAdapter.findAllPaginated(filter);
        return new PaginatedList<>(mapper.getPaginatedView(page));
    }

    @Override
    public List<UserView> getAll(UserFilter filter) {
        List<User> users = userJpaAdapter.findAll(filter);
        return mapper.getView(users);
    }

    @Override
    public UserView findByEmailId(String emailId) {
        User user = userJpaAdapter.findByEmailId(emailId);
        if (user == null) throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        return mapper.getView(user);
    }

    @Override
    public UserView blockUser(Long id) {
        User user = userJpaAdapter.findById(id);
        if (user == null) {
            log.error("No user found :{}", id);
            throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        }
        user.setStatus(EntityStatus.INACTIVE);
        userJpaAdapter.save(user);
        return mapper.getView(user);
    }

    @Override
    public UserView unBlockUser(Long id) {
        User user = userJpaAdapter.findById(id);
        if (user == null) {
            log.error("No user found :{}", id);
            throw new ServiceException(CommonErrorCodeEnum.NOT_FOUND);
        }
        user.setStatus(EntityStatus.ACTIVE);
        userJpaAdapter.save(user);
        return mapper.getView(user);
    }
}
