package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.sevices.CandidateService;
import ru.job4j.dreamjob.sevices.CityService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;



@ThreadSafe
@Controller
public class CandidateControl {

    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateControl(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

       @GetMapping("/formAddCandidate")
    public String addCandidate(Model model, HttpSession session) {
           User user = (User) session.getAttribute("user");
           if (user == null) {
               user = new User();
               user.setName("Гость");
           }
           model.addAttribute("user", user);
           model.addAttribute("cities", cityService.getAllCities());
           model.addAttribute("candidate", new Candidate(0, "Заполните поле", "Заполните поле", null, null, null));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidate.setCreated(LocalDate.now());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate")
    public String updateCandidate(Model model, @PathVariable("candidateID")int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("candidate", candidateService.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidateService.updateCandidate(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
