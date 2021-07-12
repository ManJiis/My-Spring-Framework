package top.b0x0.springbean.domain;

/**
 * @author TANG
 * @date 2021-07-12
 * @since 1.8
 */
public class Stu {
    private String name;
    private Homework homework;

    public Stu() {
    }

    public Stu(String name, Homework homework) {
        this.name = name;
        this.homework = homework;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}
