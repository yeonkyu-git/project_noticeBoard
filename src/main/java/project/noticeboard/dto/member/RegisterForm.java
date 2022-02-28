package project.noticeboard.dto.member;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterForm {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String confirmPassword;

    @NotBlank(message = "이름을 입력해주세요")
    private String username;

    @NotNull(message = "나이를 입력해주세요")
    @Range(min = 0, max = 100)
    private Integer age;
}
