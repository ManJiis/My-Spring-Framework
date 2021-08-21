package top.b0x0.spring.bean.domain;

/**
 * @author TANG
 * @date 2021-07-12
 * @since 1.8
 */
public class Homework {
    private String content;

    public Homework() {
    }

    public Homework(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
