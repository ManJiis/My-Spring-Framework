package top.b0x0.spring.sample.util;

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
public class R<T> {

    T data;

    int code;

    String msg;
}
