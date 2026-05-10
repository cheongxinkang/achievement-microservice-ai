package com.xk.achievement.controller;

import com.xk.achievement.dto.TemplateDTO;
import com.xk.achievement.model.Achievement;
import com.xk.achievement.service.AchievementService;
import com.xk.achievement.service.TemplateServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class AchievementController {

    private final AchievementService service;
    private final TemplateServiceClient templateServiceClient;

    @Autowired
    public AchievementController(AchievementService service, TemplateServiceClient templateServiceClient) {
        this.service = service;
        this.templateServiceClient = templateServiceClient;
    }

    @GetMapping
    public String listAchievements(Model model) {
        model.addAttribute("achievements", service.findAll());
        return "list";
    }

    @GetMapping("/templates")
    public String showTemplates(Model model) {
        model.addAttribute("templates", templateServiceClient.getAllTemplates());
        return "templates";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam(required = false) UUID templateId, Model model) {
        if (templateId == null) {
            return "redirect:/templates";
        }
        TemplateDTO template = templateServiceClient.getTemplateById(templateId);
        if (template == null) {
            return "redirect:/templates";
        }
        model.addAttribute("template", template);
        return "create";
    }

    @PostMapping("/save")
    public String saveAchievement(@RequestParam Map<String, String> formData) {
        Achievement achievement = new Achievement();
        
        String templateName = formData.getOrDefault("_templateName", "Generated Achievement");
        
        String description = formData.entrySet().stream()
                .filter(entry -> !entry.getKey().startsWith("_"))
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
                
        achievement.setName(templateName);
        achievement.setDescription(description);
        
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
