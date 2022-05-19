package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final static AtomicInteger CANDIDATE_ID = new AtomicInteger(3);

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java Job", "specialist", LocalDate.of(2022, 5, 15)));
        candidates.put(2, new Candidate(2, "Middle Java Job", "specialist", LocalDate.of(2022, 5, 15)));
    candidates.put(3, new Candidate(3, "Senior Java Job", "specialist", LocalDate.of(2022, 5, 15)));
    }

    public void add(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
        candidate.setId(CANDIDATE_ID.incrementAndGet());
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void updateCandidate(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }


    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
