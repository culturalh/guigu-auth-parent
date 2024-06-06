package com.atguigu.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//AllArgsConstructor:有参构造 NoArgsConstructor：无参构造
public class GuiguException extends RuntimeException{

    private int code;//状态码

    private String msg;//异常信息
}
