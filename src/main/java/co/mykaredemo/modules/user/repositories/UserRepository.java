package co.mykaredemo.modules.user.repositories;

import co.mykaredemo.enums.EntityStatus;
import co.mykaredemo.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findFirstByEmailId(String emailId);

    User findFirstByEmailIdAndStatus(String username, EntityStatus active);
}
