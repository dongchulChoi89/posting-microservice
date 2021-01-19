package com.choi.springboot.service.posts;

import com.choi.springboot.domain.posts.Posts;
import com.choi.springboot.domain.posts.PostsRepository;
import com.choi.springboot.web.dto.PostsResponseDto;
import com.choi.springboot.web.dto.PostsSaveRequestDto;
import com.choi.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository; // use constructor to inject beans using @RequiredArgsConstructor // recommend rather than @Autowired

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));
        return new PostsResponseDto(entity);
    }
}
