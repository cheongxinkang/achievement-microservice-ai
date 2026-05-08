package com.xk.achievement.service;

import com.xk.achievement.model.Achievement;
import com.xk.achievement.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementService {

    private final AchievementRepository repository;

    @Autowired
    public AchievementService(AchievementRepository repository) {
        this.repository = repository;
    }

    public List<Achievement> findAll() {
        return repository.findAll();
    }

    public Achievement save(Achievement achievement) {
        return repository.save(achievement);
    }

    public Optional<Achievement> findById(Long id) {
        return repository.findById(id);
    }

    public void complete(Long id) {
        Optional<Achievement> optional = repository.findById(id);
        if (optional.isPresent()) {
            Achievement achievement = optional.get();
            achievement.setCompleted(true);
            repository.save(achievement);
        }
    }
}
