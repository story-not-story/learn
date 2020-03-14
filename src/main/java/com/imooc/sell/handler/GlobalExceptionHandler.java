package com.imooc.sell.handler;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.SellerAuthException;
import com.imooc.sell.exception.UserException;
import com.imooc.sell.result.Result;
import com.imooc.sell.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @ExceptionHandler(UserException.class)
    public ModelAndView userExceptionHandler(){
        return new ModelAndView("user/login");
    }

    @ExceptionHandler(SellerAuthException.class)
    public ModelAndView sellerAuthException(){
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell")
                .concat(projectUrlConfig.getSell())
                .concat("/user/login"));
    }

    @ExceptionHandler(SellException.class)
    @ResponseBody
    public Result sellExceptionHandler(SellException e){
        return ResultUtil.error(e);
    }
}
