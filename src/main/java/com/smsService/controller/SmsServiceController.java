package com.smsService.controller;

import com.alibaba.fastjson.JSONObject;
import com.smsService.entity.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "sms")

public class SmsServiceController {
    private final Logger log= LoggerFactory.getLogger(SmsServiceController.class);

    @GetMapping(value = "/sendSms")
    public JSONObject sendSms(@RequestParam("paramr") String paramr,@RequestParam("mobileNo")  String mobileNo){
        boolean flag = SmsUtil.sendSmsAprove(paramr,mobileNo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", flag);
       return jsonObject;
    }


}
