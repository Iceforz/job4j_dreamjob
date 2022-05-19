package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.sevices.CandidateService;

@ThreadSafe
@Controller
public class CandidateControl {

    private final CandidateService service;

    public CandidateControl(CandidateService service) {
        this.service = service;
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", service.findAll());
        return "candidates";
    }

       @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate",
                new Candidate(0, "Заполните поле", "Заполните поле", null));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        service.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate")
    public String updateCandidate(Model model, @PathVariable("candidateID")int id) {
        model.addAttribute("candidate", service.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        service.updateCandidate(candidate);
        return "redirect:/candidates";
    }
}
