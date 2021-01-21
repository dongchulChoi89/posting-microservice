package com.choi.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Has to be located with Entity class
// When extend JpaRepository, automatically make CRUD methods
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // Add a method which SpringDataJpa does not offer
    List<Posts> findAllDesc();
}
