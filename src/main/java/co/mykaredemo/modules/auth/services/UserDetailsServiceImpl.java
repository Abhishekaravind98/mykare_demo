package co.mykaredemo.modules.auth.services;

import co.mykaredemo.enums.EntityStatus;
import co.mykaredemo.modules.auth.enums.Role;
import co.mykaredemo.modules.user.entities.User;
import co.mykaredemo.modules.user.ports.spi.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserPersistencePort userJpaAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaAdapter.findByEmailId(username);
        if (user == null || user.getStatus() != EntityStatus.ACTIVE)
            throw new UsernameNotFoundException("User not found with username: " + username);
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), buildSimpleGrantedAuthorities(user.getRole()));
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Role role) {
        if (role == null) return new ArrayList<>();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }
}
