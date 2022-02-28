package project.noticeboard.repository.postcustom;

import lombok.Data;

@Data
public class PostSearch {
    private String title;
    private String content;
    private String username;
    private int page;
    private int size;

    public PostSearch(String title, String content, String username, int page, int size) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.page = page;
        this.size = size;
    }
}
