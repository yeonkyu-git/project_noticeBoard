package project.noticeboard.repository.postcustom;

import lombok.Data;

@Data
public class PostSearch {
    private String title;
    private String content;
    private String username;
    private int offset;
    private int limit;

    public PostSearch(String title, String content, String username, int offset, int limit) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.offset = offset;
        this.limit = limit;
    }
}
