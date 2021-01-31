package com.choi.springboot.web;

import com.choi.springboot.config.auth.LoginUser;
import com.choi.springboot.config.auth.dto.SessionUser;
import com.choi.springboot.service.posts.PostsService;
import com.choi.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;  // use constructor to inject beans using @RequiredArgsConstructor // recommend rather than @Autowired

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc()); // save the list of the posts of the result of the findAllDesc() into model and send it to index.mustache

        if(user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index"; // Mustache autmatically access src/main/resources/templates and add .mustache on the file, and return index.html
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
