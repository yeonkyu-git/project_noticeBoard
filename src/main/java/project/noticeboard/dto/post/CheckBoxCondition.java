package project.noticeboard.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.noticeboard.repository.postcustom.CheckBoxSelect;

@Data
@AllArgsConstructor
public class CheckBoxCondition {
    private CheckBoxSelect condition;
    private String displayName;
}
