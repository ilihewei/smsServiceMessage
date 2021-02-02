package com.smsService.controller;

import com.alibaba.fastjson.JSONObject;

import com.smsService.entity.GeTuiUtils;
import com.smsService.entity.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "sms")

public class SmsServiceController {
    private Logger log= LoggerFactory.getLogger(SmsServiceController.class);

    @GetMapping(value = "/sendSms")
    public JSONObject sendSms(@RequestParam("paramr") String paramr,@RequestParam("mobileNo")  String mobileNo){
        boolean flag = SmsUtil.sendSmsAprove(paramr,mobileNo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", flag);
       return jsonObject;
    }

    @GetMapping(value = "/pushMessage")
    public JSONObject pushMessage(){
        try {
            Map<String,String> map=new HashMap<>();
            map.put("title","测试demo");
            map.put("content","hello A");
            map.put("mobileType","ios");
            map.put("clientId","d818db9abfa0e46e028488a0b19a6285");
            map.put("payload","40");

            GeTuiUtils.pushMessage(map);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", true);
            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", false);
            return jsonObject;
        }
    }
}
