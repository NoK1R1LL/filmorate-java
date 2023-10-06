package ru.yandex.practicum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.Mpa;
import ru.yandex.practicum.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public List<Mpa> getAllMPA() {
        return mpaService.getAllMPA();
    }

    @GetMapping("/{id}")
    public Mpa getMPAById(@PathVariable Long id) {
        return mpaService.getMPAById(id)
                .orElseThrow(() -> new RuntimeException("MPA not found with id: " + id));
    }

    @PostMapping
    public Mpa createMPA(@RequestBody Mpa mpa) {
        return mpaService.createMPA(mpa);
    }
}