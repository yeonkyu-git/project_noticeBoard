package project.noticeboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.noticeboard.config.SessionConst;
import project.noticeboard.dto.reply.ReplyForm;
import project.noticeboard.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;


    @PostMapping("/create")
    public String createReply(@ModelAttribute("replyForm") ReplyForm replyForm,
                              HttpServletRequest request) {


        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long postId = replyForm.getPostId();

        replyService.createReply(replyForm.getContent(), memberId, postId);
        return "redirect:/board/" + postId;
    }


    @GetMapping("/delete/{replyId}/{postId}")
    public String deleteReply(@PathVariable("replyId") Long replyId,
                              @PathVariable("postId") Long postId) {
        replyService.deleteReply(replyId);
        return "redirect:/board/" + postId;
    }
}
