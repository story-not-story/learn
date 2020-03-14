package com.imooc.sell.controller;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;
    @Autowired
    private ProjectUrlConfig urlConfig;
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl){
        try {
            PayResponse payResponse = payService.create(orderId);
            Map<String, Object> map = new HashMap<>();
            returnUrl = returnUrl.startsWith("http://") ? returnUrl : URLEncoder.encode(returnUrl, "utf-8");
            map.put("response", payResponse);
            map.put("returnUrl", returnUrl);
            return new ModelAndView("pay/create", map);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("url", urlConfig.getSell() + "/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
    }
    @GetMapping("/refund")
    public ModelAndView refund(@RequestParam String orderId) {
        try {
            RefundResponse refundResponse = payService.refund(orderId);
            return new ModelAndView("order/list");
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("url", urlConfig.getSell() + "/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
    }
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData){
        try {
            PayResponse payResponse = payService.asyncNotify(notifyData);
            return new ModelAndView("pay/success");
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> map = new HashMap<>();
            map.put("url", urlConfig.getSell() + "/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
    }
}
