package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final static AtomicInteger POST_ID = new AtomicInteger(3);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "specialist", LocalDate.of(2022, 5, 15)));
        posts.put(2, new Post(2, "Middle Java Job", "specialist", LocalDate.of(2022, 5, 15)));
        posts.put(3, new Post(3, "Senior Java Job", "specialist", LocalDate.of(2022, 5, 15)));
    }

    public void add(Post post) {
        posts.put(post.getId(), post);
        post.setId(POST_ID.incrementAndGet());
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void updatePost(Post post) {
        posts.replace(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}