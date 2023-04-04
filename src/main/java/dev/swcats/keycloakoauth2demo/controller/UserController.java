package dev.swcats.keycloakoauth2demo.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/")
    public String getIndex(Authentication auth, Model model) {
        model.addAttribute("isAuthenticated", auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken));
        return "index";
    }

    @GetMapping("/somebody")
    public String getSomebody(Authentication auth, Model model) {
        model.addAttribute("isAuthenticated", auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken));
        return "somebody";
    }

    @GetMapping("/anonymous")
    public String getAnonymous(Authentication auth, Model model) {
        return "anonymous";
    }
}
