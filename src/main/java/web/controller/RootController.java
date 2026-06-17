package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RootController {
    @RequestMapping(value = "/")
    public String getHomePage(Model model) {
        List<String> messages = new ArrayList<>();
        messages.add("Добро пожаловать!");
        model.addAttribute("messages", messages);
        return "helloPage";
    }

    @GetMapping(value = "login")
    public String getLoginPage() {
        return "loginPage";
    }


}
