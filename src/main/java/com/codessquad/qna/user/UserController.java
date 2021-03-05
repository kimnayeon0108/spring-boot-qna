package com.codessquad.qna.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String create(User user) {
        System.out.println("user : " + user);
        user.setIndex(users.size() + 1 + "");
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", getUserByUserId(userId));
        return "user/profile";

    }

    private User getUserByUserId(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/users/{index}/form")
    public String updateForm(@PathVariable String index, Model model) {
        model.addAttribute("user", getUserByIndex(index));
        return "/user/updateForm";
    }

    private User getUserByIndex(String index) {
        for (User user : users) {
            if (user.getIndex().equals(index)) {
                return user;
            }
        }
        return null;
    }

    @PostMapping("/users/{index}")
    public String update(@PathVariable String index, User newUser) {
        User user = getUserByIndex(index);
        user.update(newUser);
        return "redirect:/users";
    }
}
