package com.choi.springboot.web;

import com.choi.springboot.domain.posts.PostsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT) // *****
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate; // *****

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void indexPageIsloaded() {
        // When
        String body = restTemplate.getForObject("/", String.class);

        // Then
        assertThat(body).contains("Web Service using Spring Boot");
    }
}
