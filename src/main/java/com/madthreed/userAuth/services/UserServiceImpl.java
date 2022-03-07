package com.madthreed.userAuth.services;

import com.madthreed.userAuth.domain.Role;
import com.madthreed.userAuth.domain.Token;
import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.handlers.UserServiceException;
import com.madthreed.userAuth.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final TokenService tokenService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    //todo registerNewUser - send verification
    @Override
    public void registerNewUser(User user) throws UserServiceException {
        String message;

        User userFromDb = userRepo.findByUsername(user.getUsername());

        //todo check user with same email

        if (userFromDb != null) {
            message = "User exists";
            throw new UserServiceException(message);
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));

        userRepo.save(user);
    }


    // todo updateUser - UserDTO
    @Override
    public void updateUser(User user, Map<String, String> form) {

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        user.setId(Long.parseLong(form.get("id")));
        user.setUsername(form.get("username"));
//        user.setPassword(form.get("password"));
        user.setFirstname(form.get("firstname"));
        user.setLastname(form.get("lastname"));
        user.setEmail(form.get("email"));
        if (form.containsKey("active")) {
            user.setActive(form.get("active").equals("on"));
        } else {
            user.setActive(false);
            tokenService.deactivateTokenByUser(user);
        }

        userRepo.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


    @Override
    public void deleteUser(User user) {
        userRepo.delete(user); //todo deleting SU must be disabled
    }


    @Override
    public void sendNewCredentials(User user) throws UserServiceException {
        // Password stores in String, because we send it to user in E-mail
        // There's no need to use char[] or some else

        String newPwd = tokenService.generateTokenAndPwdForUser(user);

        try {
            mailService.sendActivationMail(user, newPwd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserServiceException("send activation mail error", e.getCause());
        }
    }


    //todo activateUserByToken - error handling by exceptions
    @Override
    public void activateUserByToken(String token) throws UserServiceException {
        User user = tokenService.getUserByToken(token);

        if (user == null) {
            throw new UserServiceException("invalid token");
        }

        Token verificationToken = user.getVerificationToken();

        if (verificationToken == null) {
            throw new UserServiceException("invalid token");
        }

//        if (user.isActive()) {
//            tokenService.deactivateTokenByUser(user);
//            return "User '" + user.getUsername() + "' already active";
//        }

        Calendar calendar = Calendar.getInstance();
        long localDate = calendar.getTime().getTime();
        long expiryDate = verificationToken.getExpiryDate().getTime();
        if ((expiryDate - localDate) <= 0) {
            throw new UserServiceException("token expired");
        }

        user.setVerificationToken(null);
        user.setActive(true);
        userRepo.save(user);

        tokenService.deactivateTokenByUser(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails suUser =
//                org.springframework.security.core.userdetails.User.builder()
//                        .username("su")
//                        .password("{bcrypt}$2a$12$Tg9zzvg61FuiwsWN.YU7ju4u7V15/vPnrjs.uAMY0sKMMm0amNLkm")
//                        .roles("SUPERUSER", "ADMIN", "USER")
//                        .build();
//
//        if (username.equals(suUser.getUsername())) {
//            return suUser;
//        }
//
//        User user = userRepo.findByUsername(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        return new UserServicePrincipal(user);
//    }
}
