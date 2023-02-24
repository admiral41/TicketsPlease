package com.example.ticket_please.controller;

import com.example.ticket_please.Entity.User;
import com.example.ticket_please.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    static final class Routes {


        static final String LOGIN = "/login";
        static final String REGISTER = "/register";
    }

    private final UserService userService;

    @GetMapping(Routes.LOGIN)
    public String login(final Principal principal) {
        return userService.login(principal);
    }

    @GetMapping(Routes.REGISTER)
    public String register(@ModelAttribute final User user, final Model model) {
        return userService.register(model);
    }

    @PostMapping(Routes.REGISTER)
    public String registerOk(@Valid @ModelAttribute("user") final User user, final BindingResult bindingResult) {
        return userService.registerSuccessfully(user, bindingResult);
    }
}