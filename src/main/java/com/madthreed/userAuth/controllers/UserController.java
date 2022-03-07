package com.madthreed.userAuth.controllers;

import com.madthreed.userAuth.domain.Role;
import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.handlers.UserServiceException;
import com.madthreed.userAuth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by MadThreeD on 2022.
 */

@Controller
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('SUPERUSER') || hasAuthority('ADMIN')")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String userList(Model model) {
        List<User> allUsers = userService.getAllUsers();
        allUsers.sort(Comparator.comparing(User::getUsername));

        model.addAttribute("usersDto", allUsers);
        model.addAttribute("message", "");

        return "user_list";
    }


    @GetMapping("/add")
    public String add() {

        return "user_add";
    }


    @PostMapping("/add")
    public String addUser(User userDto, Model model) {
        String message;
        String message2;

        try {
            userService.registerNewUser(userDto);
        } catch (UserServiceException e) {
            e.printStackTrace();
            message = "Couldn't register new user: " + e.getMessage();
            model.addAttribute("message", message);
            return "user_add";
        }

        try {
            userService.sendNewCredentials(userDto);
            message = "User added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Couldn't send new credentials: " + e.getMessage();
            message2 = "You must send activation's credentials by your hands from 'Edit user'";
            model.addAttribute("message2", message2);
        }

        model.addAttribute("message", message);

        return "message";
    }


    @PostMapping("/edit")
    public String userEdit(@RequestParam("id") User userDto, Model model) {

        model.addAttribute("userDto", userDto);
        model.addAttribute("roles", Role.values());

        return "user_edit";
    }


    @PutMapping("/edit")
    public String userSave(@RequestParam Map<String, String> form, @RequestParam("id") User userDto, Model model) {
        userService.updateUser(userDto, form);

        model.addAttribute("message", "User updated successfully");

        return "message";
    }


    @DeleteMapping("/delete")
    public String userDelete(@RequestParam("id") User userDto, Model model) {
        String message;

        Set<Role> roles = userDto.getRoles();

        if (!roles.contains(Role.SUPERUSER)) {
            userService.deleteUser(userDto);
            message = "User deleted successfully";
        } else {
            message = "Couldn't delete SUPERUSER";
        }

        model.addAttribute("message", message);

        return "message";
    }


    @PostMapping("/activation")
    public String activation(@RequestParam("id") User userDto, Model model) {
        String message;

        try {
            userService.sendNewCredentials(userDto);
            message = "Successfully sent new credentials";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Couldn't connect to mail server: " + e.getMessage();

        }

        model.addAttribute("message", message);

        return "message";
    }

    @GetMapping("/activate/{token}")
    public String activate(@PathVariable String token, Model model) {
        String message;

        try {
            userService.activateUserByToken(token);
            message = "User successfully activated";
        } catch (UserServiceException e) {
            e.printStackTrace();
            message = "Couldn't activate user: " + e.getMessage();
        }

        model.addAttribute("message", message);

        return "message";
    }
}
