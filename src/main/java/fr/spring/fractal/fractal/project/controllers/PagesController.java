package fr.spring.fractal.fractal.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @RequestMapping("/home")
    public String imagePage() {
        return "image";
    }

    @RequestMapping("/")
    public String homePage() {
        return "home";
    }
}
