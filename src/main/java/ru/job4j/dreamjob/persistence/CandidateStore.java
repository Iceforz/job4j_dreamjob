package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final static AtomicInteger CANDIDATE_ID = new AtomicInteger(3);

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

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

}
