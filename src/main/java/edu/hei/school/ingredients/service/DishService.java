package edu.hei.school.ingredients.service;

import edu.hei.school.ingredients.entity.Dish;
import edu.hei.school.ingredients.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }
}
