package com.api.pokemonreview.security;

import com.api.pokemonreview.models.Role;
import com.api.pokemonreview.models.UserEntity;
import com.api.pokemonreview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        Collection<GrantedAuthority> userRolesAsAuthorities = mapRolesToAuthorities(user.getRoles());

        return new User(user.getUsername(), user.getPassword(), userRolesAsAuthorities);
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        List<String> rolesNames = roles.stream().map(Role::getName).toList();
        return rolesNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
