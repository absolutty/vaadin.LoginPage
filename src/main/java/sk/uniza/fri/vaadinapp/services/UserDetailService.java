package sk.uniza.fri.vaadinapp.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.uniza.fri.vaadinapp.models.User;
import sk.uniza.fri.vaadinapp.repositories.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }

    public boolean emailDoesntExists(String email) {
        User userWithEmail = userRepository.findUserByEmail(email);
        return userWithEmail == null;
    }

    public void tryRegisterUser(User toBeRegistered) throws IllegalArgumentException {
        if (emailDoesntExists(toBeRegistered.getEmail())) {
            userRepository.save(toBeRegistered);
            userRepository.flush();
        } else {
            throw new IllegalArgumentException("User with email " + toBeRegistered.getEmail() + " already exists.");
        }
    }

}
