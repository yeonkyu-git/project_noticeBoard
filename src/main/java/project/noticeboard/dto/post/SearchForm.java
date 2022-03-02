package project.noticeboard.dto.post;

import lombok.Data;
import project.noticeboard.repository.postcustom.CheckBoxSelect;

@Data
public class SearchForm {

    private CheckBoxSelect condition;
    private String search;

}
