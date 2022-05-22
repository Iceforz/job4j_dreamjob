package ru.job4j.dreamjob.sevices;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;
import ru.job4j.dreamjob.store.PostDBStore;

import java.util.Collection;
import java.util.List;

@Service
public class PostService {

    private final PostDBStore store;
    private final CityService cityService;

    public PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Post> findAll() {
        List<Post> posts = store.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return store.findAll();
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void updatePost(Post post) {
        store.updatePost(post);
    }
}