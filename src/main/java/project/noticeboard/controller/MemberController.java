package project.noticeboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.noticeboard.config.SessionConst;
import project.noticeboard.dto.member.LoginForm;
import project.noticeboard.dto.member.RegisterForm;
import project.noticeboard.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String homeLogin(Model model) {
        model.addAttribute("memberForm", new LoginForm());
        return "view/login";
    }

    @PostMapping("/")
    public String login(@Validated @ModelAttribute("memberForm") LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request) throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "view/login";
        }

        Long memberId = memberService.login(form.getEmail(), form.getPassword());
        log.info("login Id : {}", memberId);

        // 로그인 시 세션에 멤버 ID 저장
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberId);

        return "redirect:/board";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "view/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("registerForm") RegisterForm form,
                           BindingResult bindingResult) {
        if (!form.getConfirmPassword().equals(form.getPassword())) {
            bindingResult.reject("passwordMismatch");
        }

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "view/register";
        }

        Long joinId = memberService.join(form.getEmail(), form.getPassword(), form.getUsername(), form.getAge());
        log.info("joinId : {}", joinId);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
