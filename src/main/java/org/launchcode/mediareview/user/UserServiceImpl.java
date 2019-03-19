package org.launchcode.mediareview.user;

import org.launchcode.mediareview.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Transactional
    @Override
    public User save(UserDto userDto) throws AccountExistsException {
        boolean usernameExists;
        boolean emailExists;

        User existingUser = userRepository.findByUsername(userDto.getUsername());
        usernameExists = accountExistenceCheck(existingUser);

        existingUser = userRepository.findByEmail(userDto.getEmail());
        emailExists = accountExistenceCheck(existingUser);

        if (usernameExists || emailExists) {
            throw new AccountExistsException(usernameExists, emailExists);
        }

        User user = new User(userDto.getUsername(), userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public boolean accountExistenceCheck(User existingUser) {
        if (existingUser != null) {
            return true;
        } else {
            return false;
        }
    }
}
