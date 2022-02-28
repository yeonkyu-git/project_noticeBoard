package project.noticeboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.noticeboard.config.SessionConst;
import project.noticeboard.dto.PostDto;
import project.noticeboard.dto.post.PostDetailForm;
import project.noticeboard.dto.post.PostEditForm;
import project.noticeboard.dto.post.PostForm;
import project.noticeboard.entity.Post;
import project.noticeboard.repository.PostRepository;
import project.noticeboard.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping
    public String boardView(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                            Model model) {

        Page<PostDto> allPost = postService.findAllPost(page, size);

        model.addAttribute("posts", allPost.getContent());
        model.addAttribute("totalPages", allPost.getTotalPages());
        model.addAttribute("currentPage", page);

        log.info("totalPages : {}", allPost.getTotalPages());
        log.info("currentPage : {}", page);


        for (PostDto postDto : allPost.getContent()) {
            log.info("postDto Created : {}", postDto.getCreatedAt());
        }

        return "view/board";
    }

    @GetMapping("/add")
    public String boardAddForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "view/contentEnroll";
    }

    @PostMapping("/add")
    public String boardAdd(@Validated @ModelAttribute("postForm") PostForm form,
                           BindingResult bindingResult,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "view/contentEnroll";
        }


        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long postId = postService.createPost(form.getTitle(), form.getContent(), memberId);

        return "redirect:/board";
    }

    @GetMapping("/{postId}")
    public String boardDetail(@PathVariable("postId") Long postId,
                              Model model) {

        log.info("board Detail");
        Optional<Post> postOp = postRepository.findById(postId);
        Post originPost = postOp.orElse(null);
        PostDetailForm post = new PostDetailForm();
        post.setTitle(originPost.getTitle());
        post.setContent(originPost.getContent());
        post.setPostId(originPost.getId());
        post.setMemberId(originPost.getMember().getId());

        model.addAttribute("post", post);

        return "view/boardDetail";
    }

    @GetMapping("/edit/{postId}")
    public String postEditView(@PathVariable("postId") Long postId, Model model) {
        Optional<Post> postOp = postRepository.findById(postId);
        Post originPost = postOp.orElse(null);
        PostEditForm post = new PostEditForm();
        post.setTitle(originPost.getTitle());
        post.setContent(originPost.getContent());
        post.setPostId(originPost.getId());
        post.setMemberId(originPost.getMember().getId());

        model.addAttribute("post", post);

        return "view/contentEdit";
    }

    @PostMapping("/edit/{postId}")
    public String postEdit(@PathVariable("postId") Long postId,
                           @ModelAttribute("post") PostEditForm post,
                           HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        postService.updatePost(postId, memberId, post.getTitle(), post.getContent());

        return "redirect:/board/" + postId;
    }


    @GetMapping("/delete/{postId}")
    public String postDelete(@PathVariable("postId") Long postId,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);

        postService.deletePost(postId, memberId);
        return "redirect:/board";
    }
}
