package com.smsService.entity;

import com.gexin.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;


/**
 * @author  lihewei
 */
@Slf4j
@Component
public class GeTuiUtils {



    private  static   String appId;


    private   static String appKey;


    private  static String masterSecret;


    @Value("${getui.OfflineExpireTime}")
    private  static   long offlineExpireTime;


    @Value("${getui.AppID}")
    public  void setAppId(String appId) {
        GeTuiUtils.appId = appId;
    }
    @Value("${getui.AppKey}")
    public  void setAppKey(String appKey) {
        GeTuiUtils.appKey = appKey;
    }

    @Value("${getui.MasterSecret}")
    public  void setMasterSecret(String masterSecret) {
        GeTuiUtils.masterSecret = masterSecret;
    }


    /**
     * 对单个用户推送消息，应用场景，充值，消费
     *
     * 场景1：某用户发生了一笔交易，银行及时下发一条推送消息给该用户。
     *
     * 场景2：用户定制了某本书的预订更新，当本书有更新时，需要向该用户及时下发一条更新提醒信息。
     * 这些需要向指定某个用户推送消息的场景，即需要使用对单个用户推送消息的接口。
     */
    public static void pushMessage(Map<String,String> map) {

        IGtPush push =new IGtPush(appKey, masterSecret);
        //获取参数中的ClientID和mobileType
        String clientId=map.get("clientId");
        String mobileType=map.get("mobileType");
        //如果clientId和手机类型为空，不进行消息推送
        if(clientId==null||mobileType==null){
            return;
        }
        //发送透传的消息
        TransmissionTemplate template = new TransmissionTemplate();
        //设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        //IOS和Android的透传消息推送模式有差异，根据mobileType进行推送
        if(mobileType.equalsIgnoreCase("ios")){
            APNPayload payload = new APNPayload();
            payload.setAutoBadge("+1");
//            payload.setContentAvailable(1);
            payload.setSound("default");

            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setTitle(map.get("title"));
            alertMsg.setBody(map.get("content"));
            payload.setAlertMsg(alertMsg);
            payload.addCustomMsg("payload",map.get("payload"));
            template.setAPNInfo(payload);
        }else{
            //透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
            template.setTransmissionType(1);
            String jsonString = JSON.toJSONString(map);
            template.setTransmissionContent(jsonString);
        }
            SingleMessage message = new SingleMessage();
            message.setData(template);
            message.setOffline(true);

            //时间单位为毫秒，5分钟
            message.setOfflineExpireTime(500*1000);

            // 单推消息类型
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(clientId);
            IPushResult ret = null;

        try {
            ret = push.pushMessageToSingle(message,target);
        } catch (RequestException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }


    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        Map<String,String> map=new HashMap<>();
        map.put("title","测试demo");
        map.put("content","hello A");
        map.put("mobileType","ios");
        map.put("clientId","d818db9abfa0e46e028488a0b19a6285");
        map.put("payload","40");
       // new GeTuiUtils();
        pushMessage(map);

    }

}

