package com.choi.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Has to be located with Entity class
// When extend JpaRepository, automatically make CRUD methods
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
