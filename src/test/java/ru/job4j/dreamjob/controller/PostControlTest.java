package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.services.CityService;
import ru.job4j.dreamjob.services.PostService;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class PostControlTest {
    @Test
    public void whenPosts() {

        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "job1", LocalDate.now(), new City()),
                new Post(2, "New post", "job2", LocalDate.now(), new City()));

        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService,
                cityService
        );
        String page = postControl.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "job", LocalDate.now(), new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService,
                cityService
        );
        String page = postControl.createPost(input);
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "New post", "job", LocalDate.now(), new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService,
                cityService
        );
        String page = postControl.updatePost(input);
        verify(postService).updatePost(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormAddPost() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService,
                cityService
        );
        String page = postControl.addPost(model, session);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFormUpdatePost() {
        Post input = new Post(1, "New post", "job", LocalDate.now(), new City());
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(session.getAttribute(any())).thenReturn(new User());
        when(postService.findById(anyInt())).thenReturn(input);
        CityService cityService = mock(CityService.class);
        PostControl postControl = new PostControl(
                postService,
                cityService
        );
        String page = postControl.updatePost(model, 1 , session);
        verify(model).addAttribute("post", input);
        assertThat(page, is("updatePost"));
    }
}

