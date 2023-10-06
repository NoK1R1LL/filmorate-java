package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Mpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MpaService {
    private final List<Mpa> mpas = new ArrayList<>();
    private long nextMpaId = 1;

    public List<Mpa> getAllMPA() {
        return mpas;
    }

    public Optional<Mpa> getMPAById(Long id) {
        return mpas.stream().filter(mpa -> mpa.getId().equals(id)).findFirst();
    }

    public Mpa createMPA(Mpa mpa) {
        mpa.setId(nextMpaId);
        nextMpaId++;
        mpas.add(mpa);
        return mpa;
    }
}
