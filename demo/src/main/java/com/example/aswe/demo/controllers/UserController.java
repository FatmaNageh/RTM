package com.example.aswe.demo.controllers;

import com.example.aswe.demo.models.User;
import com.example.aswe.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // List all users
    @GetMapping("")
    public ModelAndView getUsers() {
        ModelAndView mav = new ModelAndView("list-users.html");
        List<User> users = this.userRepository.findAll();
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("/Registeration")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView("registeration.html");
        User newUser = new User();
        mav.addObject("users", newUser);
        return mav;
    }

    @PostMapping("/Registeration")
    public String saveUser(@ModelAttribute User user) {
        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);
        user.setRole("USER"); // Default role
        this.userRepository.save(user);
        return "Added";
    }

    @GetMapping("/Login")
    public ModelAndView Login() {
        ModelAndView mav = new ModelAndView("login.html");
        User newUser = new User();
        mav.addObject("users", newUser);
        return mav;
    }

    @PostMapping("/Login")
    public RedirectView loginProcess(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User dbuser = this.userRepository.findByUsername(username);
        if (dbuser != null && BCrypt.checkpw(password, dbuser.getPassword())) {
            session.setAttribute("username", dbuser.getUsername());
            session.setAttribute("role", dbuser.getRole());
            return new RedirectView("Profile");
        } else {
            return new RedirectView("Login");
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/users/Login");
    }

    @GetMapping("/Profile")
    public ModelAndView viewProfile(HttpSession session) {
        ModelAndView mav = new ModelAndView("profile.html");
        mav.addObject("username", (String) session.getAttribute("username"));
        return mav;
    }

    // Update user
    @GetMapping("/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ModelAndView mav = new ModelAndView("update-user.html");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setName(user.getName());
        existingUser.setUsername(user.getUsername());
        existingUser.setDob(user.getDob());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
            existingUser.setPassword(encodedPassword);
        }
        userRepository.save(existingUser);
        return "redirect:/users";
    }

    // Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/search")
    public ModelAndView searchUsers(@RequestParam(name = "name", required = false) String name) {
        ModelAndView mav = new ModelAndView("search-results.html");
        if (name != null && !name.isEmpty()) {
            List<User> users = this.userRepository.findByNameContainingIgnoreCase(name);
            mav.addObject("users", users);
            mav.addObject("searchTerm", name);
        } else {
            mav.addObject("users", this.userRepository.findAll());
        }
        return mav;
    }

    @GetMapping("/edit-role/{id}")
public ModelAndView editRoleForm(@PathVariable("id") int id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

    ModelAndView mav = new ModelAndView("edit-role.html");
    mav.addObject("user", user);
    return mav;
}

@PostMapping("/edit-role/{id}")
public RedirectView updateRole(@PathVariable("id") int id, @RequestParam("role") String role) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    user.setRole(role);
    userRepository.save(user);
    return new RedirectView("/users");
}

}
