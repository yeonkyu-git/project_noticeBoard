package project.noticeboard.repository.postcustom;

import lombok.Data;

@Data
public class PostSearch {
    private String title;
    private String content;
    private String username;
    private int offset;
    private int limit;
}
