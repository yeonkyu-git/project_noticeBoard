package project.noticeboard.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

}
