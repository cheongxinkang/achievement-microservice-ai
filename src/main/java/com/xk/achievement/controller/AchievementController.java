package com.xk.achievement.controller;

import com.xk.achievement.model.Achievement;
import com.xk.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class AchievementController {

    private final AchievementService service;

    @Autowired
    public AchievementController(AchievementService service) {
        this.service = service;
    }

    @GetMapping
    public String listAchievements(Model model) {
        model.addAttribute("achievements", service.findAll());
        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("achievement", new Achievement());
        return "mock-create";
    }

    @PostMapping("/save")
    public String saveAchievement(@ModelAttribute Achievement achievement) {
        service.save(achievement);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Achievement> achievement = service.findById(id);
        if (achievement.isPresent()) {
            model.addAttribute("achievement", achievement.get());
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateAchievement(@PathVariable Long id, @ModelAttribute Achievement achievementDetails) {
        Optional<Achievement> optional = service.findById(id);
        if (optional.isPresent()) {
            Achievement achievement = optional.get();
            achievement.setName(achievementDetails.getName());
            achievement.setDescription(achievementDetails.getDescription());
            service.save(achievement);
        }
        return "redirect:/";
    }

    @PostMapping("/complete/{id}")
    public String completeAchievement(@PathVariable Long id) {
        service.complete(id);
        return "redirect:/";
    }
}
