package project.noticeboard.repository.postcustom;

import lombok.Data;

@Data
public class PostSearch {
    private CheckBoxSelect checkBoxSelect;
    private String conditionString;
    private int page;
    private int size;

    public PostSearch(CheckBoxSelect checkBoxSelect, String conditionString, int page, int size) {
        this.checkBoxSelect = checkBoxSelect;
        this.conditionString = conditionString;
        this.page = page;
        this.size = size;
    }
}
