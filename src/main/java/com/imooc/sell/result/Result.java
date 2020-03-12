package com.imooc.sell.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Result class
 *
 * @author hujun
 * @date 2020/02/05
 */
@Data
public class Result<T>  implements Serializable {
    private static final long serialVersionUID = -2648750251438985017L;
    private Integer code;
    private String msg;
    private T data;
}
