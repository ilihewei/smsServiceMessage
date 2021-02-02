package com.smsService.controller;

import com.alibaba.fastjson.JSONObject;
import com.smsService.entity.GeTuiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lihewei
 */
@RestController
@RequestMapping(value="getui")
public class GeTuiServiceController {

    @PostMapping(value="/pushMessage")
    public JSONObject pushMessage(@RequestBody Map<String,String> map){
        try {
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
