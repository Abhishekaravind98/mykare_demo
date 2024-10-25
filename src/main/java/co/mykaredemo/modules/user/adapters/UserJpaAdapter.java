package co.mykaredemo.modules.user.adapters;

import co.mykaredemo.enums.EntityStatus;
import co.mykaredemo.modules.user.entities.User;
import co.mykaredemo.modules.user.misc.UserFilter;
import co.mykaredemo.modules.user.ports.spi.UserPersistencePort;
import co.mykaredemo.modules.user.repositories.UserRepository;
import co.mykaredemo.modules.user.specifications.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserJpaAdapter extends UserSpecification<User> implements UserPersistencePort {
    private final UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByEmailId(String emailId) {
        return repository.findFirstByEmailId(emailId);
    }

    @Override
    public Page<User> findAllPaginated(UserFilter filter) {
        Specification<User> specification = Specification.where(query(filter.getQuery()))
                .and(entityStatus(filter.getStatus()));
        return repository.findAll(specification, filter.filterPageable());
    }

    @Override
    public List<User> findAll(UserFilter filter) {
        Specification<User> specification = Specification.where(query(filter.getQuery()))
                .and(entityStatus(filter.getStatus()));
        return repository.findAll(specification);
    }

    @Override
    public User findByEmailIdAndStatus(String username) {
        return repository.findFirstByEmailIdAndStatus(username, EntityStatus.ACTIVE);
    }
}
