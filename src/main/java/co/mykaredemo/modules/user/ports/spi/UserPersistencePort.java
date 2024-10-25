package co.mykaredemo.modules.user.ports.spi;

import co.mykaredemo.modules.user.entities.User;
import co.mykaredemo.modules.user.misc.UserFilter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserPersistencePort {
    User save(User user);

    User findById(Long id);

    User findByEmailId(String emailId);

    Page<User> findAllPaginated(UserFilter filter);

    List<User> findAll(UserFilter filter);

    User findByEmailIdAndStatus(String username);
}
