package com.choi.springboot.service.posts;

import com.choi.springboot.domain.posts.Posts;
import com.choi.springboot.domain.posts.PostsRepository;
import com.choi.springboot.web.dto.PostsListResponseDto;
import com.choi.springboot.web.dto.PostsResponseDto;
import com.choi.springboot.web.dto.PostsSaveRequestDto;
import com.choi.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Never use Entity class as Dto class
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository; // use constructor to inject beans using @RequiredArgsConstructor // recommend rather than @Autowired

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId(); // postsRepository.save()/delete() takes Posts object // postsRepository.CRUD() returns Posts object
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) { // ***** // except update(), JpaRepository offers CRD
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent()); // *****
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // Keep the scope of trancsaction, and read is possible, when there is no save,update, delete, recommend to use to high the speed
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // == posts -> new PostsListResponseDto(posts) // make the result of the stream of the postsRepository into the PostsListResponseDtos
                .collect(Collectors.toList()); // and then make those into list
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no post. id=" + id));
        postsRepository.delete(posts);
    }
}
