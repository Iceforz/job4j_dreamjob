package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.sevices.CityService;
import ru.job4j.dreamjob.sevices.PostService;

@ThreadSafe
@Controller
public class PostControl {

    private final PostService postservice;
    private final CityService cityService;

    public PostControl(PostService postservice, CityService cityService) {
        this.postservice = postservice;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postservice.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("post", new Post(0, "Заполните поле", "Заполните поле", null, false, null));
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        postservice.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost")
    public String updatePost(Model model, @PathVariable("postId")int id) {
        model.addAttribute("post", postservice.findById(id));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        postservice.updatePost(post);
        return "redirect:/posts";
    }
}