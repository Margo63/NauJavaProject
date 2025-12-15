package ru.margarita.NauJava.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.margarita.NauJava.repositories.UserRepository;
import ru.margarita.NauJava.entities.User;

/**
 * Класс для обработки авторизации
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-11-2
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;
    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException
    {
        List<User> appUser = userRepository.findByName(name);
        if (!appUser.isEmpty())
        {
            return new org.springframework.security.core.userdetails.User(
                    appUser.getFirst().getName(), appUser.getFirst().getPassword(),
                    mapRoles(appUser.getFirst()));
        }
        else
        {
            throw new UsernameNotFoundException("user not found");
        }
    }
    private Collection<GrantedAuthority> mapRoles(User appUser)
    {
        Collection<GrantedAuthority> collect = new ArrayList<>(List.of());
        String role;
        if(appUser.getAdmin()) role = "ADMIN";
        else role = "USER";
        collect.add(new SimpleGrantedAuthority("ROLE_" + role));
        return collect;
    }
}