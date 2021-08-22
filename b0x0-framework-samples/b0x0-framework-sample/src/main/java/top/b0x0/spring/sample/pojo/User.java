package top.b0x0.spring.sample.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String name;
}
