package project.noticeboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import project.noticeboard.config.SessionConst;
import project.noticeboard.dto.PostDto;
import project.noticeboard.dto.ReplyDto;
import project.noticeboard.dto.post.*;
import project.noticeboard.dto.reply.ReplyForm;
import project.noticeboard.entity.Post;
import project.noticeboard.repository.PostRepository;
import project.noticeboard.repository.postcustom.CheckBoxSelect;
import project.noticeboard.repository.postcustom.PostSearch;
import project.noticeboard.service.PostService;
import project.noticeboard.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final ReplyService replyService;

//    @GetMapping
    public String boardView(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                            Model model) {

        Page<PostDto> allPost = postService.findAllPost(page, size);

        model.addAttribute("checkBoxConditions", checkBoxConditions());
        model.addAttribute("posts", allPost.getContent());
        model.addAttribute("totalPages", allPost.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchForm", new SearchForm());

        return "view/board";
    }

    // 검색 조건을 쿼리파마리터로 받아서 전체 조회, 조건 조회 모두 통합함
    @GetMapping
    public String boardViewV2(
                            @RequestParam(value = "condition", required = false, defaultValue = "ALL") CheckBoxSelect checkBoxSelect,
                            @RequestParam(value = "search", required = false, defaultValue = "") String search,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                            Model model) {

        SearchForm form = new SearchForm();
        log.info("checkBoxSelect = {} , search = {}", checkBoxSelect, search);
        form.setCondition(CheckBoxSelect.valueOf(checkBoxSelect.name()));
        form.setSearch(search);

        PostSearch postSearch = new PostSearch(form.getCondition(), form.getSearch(), page, size);
        Page<PostDto> allPost = postService.findBySearch(postSearch);

        model.addAttribute("checkBoxConditions", checkBoxConditions());
        model.addAttribute("posts", allPost.getContent());
        model.addAttribute("totalPages", allPost.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("searchForm", form);
        model.addAttribute("condition", checkBoxSelect);
        model.addAttribute("search", search);

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
    public String boardDetail(@PathVariable("postId") Long postId, Model model) {

        log.info("board Detail");
        // Post Detail
        Optional<Post> postOp = postRepository.findById(postId);
        Post originPost = postOp.orElse(null);
        PostDetailForm post = new PostDetailForm();
        post.setTitle(originPost.getTitle());
        post.setContent(originPost.getContent());
        post.setPostId(originPost.getId());
        post.setMemberId(originPost.getMember().getId());

        //Reply Detail
        List<ReplyDto> replies = replyService.findByPostId(originPost.getId());


        model.addAttribute("post", post);
        model.addAttribute("replies", replies);
        model.addAttribute("replyForm", new ReplyForm());

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




    private List<CheckBoxCondition> checkBoxConditions() {
        List<CheckBoxCondition> conditions = new ArrayList<>();
        conditions.add(new CheckBoxCondition(CheckBoxSelect.TITLE, "제목"));
        conditions.add(new CheckBoxCondition(CheckBoxSelect.CONTENT, "내용"));
        conditions.add(new CheckBoxCondition(CheckBoxSelect.TITLEANDCONTENT, "제목+내용"));
        conditions.add(new CheckBoxCondition(CheckBoxSelect.WRITER, "작성자"));

        return conditions;
    }
}
