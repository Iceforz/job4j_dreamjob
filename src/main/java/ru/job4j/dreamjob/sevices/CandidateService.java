package ru.job4j.dreamjob.sevices;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;

import java.util.Collection;
import java.util.List;

@Service
public class CandidateService {

    private final CandidateDbStore store;
    private final CityService cityService;

    public CandidateService(CandidateDbStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        List<Candidate> candidates = store.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return store.findAll();
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void updateCandidate(Candidate candidate) {
        store.updateCandidate(candidate);
    }
}