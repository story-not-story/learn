package com.imooc.sell.handler;

import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.UserException;
import com.imooc.sell.result.Result;
import com.imooc.sell.util.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ModelAndView userExceptionHandler(){
        return new ModelAndView("user/login");
    }

    @ExceptionHandler(SellException.class)
    @ResponseBody
    public Result sellExceptionHandler(SellException e){
        return ResultUtil.error(e);
    }
}
