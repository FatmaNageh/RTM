package com.example.aswe.demo.controllers;

import com.example.aswe.demo.models.User;
import com.example.aswe.demo.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ModelAndView listUsers() {
        List<User> users = userService.findAll();
        ModelAndView mav = new ModelAndView("list-users.html");
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView("register.html");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/register")
    public RedirectView register(@ModelAttribute User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        user.setRole("USER");
        userService.save(user);
        return new RedirectView("/users");
    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login.html");
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            return new RedirectView("/users/profile");
        }
        return new RedirectView("/users/login");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/users/login");
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) {
        ModelAndView mav = new ModelAndView("profile.html");
        mav.addObject("username", session.getAttribute("username"));
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable int id) {
        User user = userService.findById(id);
        ModelAndView mav = new ModelAndView("update-user.html");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit/{id}")
    public RedirectView updateUser(@PathVariable int id, @ModelAttribute User user) {
        User existing = userService.findById(id);
        existing.setName(user.getName());
        existing.setUsername(user.getUsername());
        existing.setDob(user.getDob());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        }
        userService.save(existing);
        return new RedirectView("/users");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id) {
        userService.deleteById(id);
        return new RedirectView("/users");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(required = false) String name) {
        List<User> users = (name != null && !name.isEmpty()) ? userService.searchByName(name) : userService.findAll();
        ModelAndView mav = new ModelAndView("search-results.html");
        mav.addObject("users", users);
        mav.addObject("searchTerm", name);
        return mav;
    }

    @GetMapping("/edit-role/{id}")
    public ModelAndView editRoleForm(@PathVariable int id) {
        User user = userService.findById(id);
        ModelAndView mav = new ModelAndView("edit-role.html");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit-role/{id}")
    public RedirectView updateRole(@PathVariable int id, @RequestParam String role) {
        userService.updateRole(id, role);
        return new RedirectView("/users");
    }

    @GetMapping("/test")
    public ModelAndView testPage() {
        return new ModelAndView("test.html");
    }
}
