package com.smsService.entity;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import java.util.HashMap;
import java.util.Map;



public class SmsUtil {
    //短信验证码模板 ID
    public static final String SMS_TEMPLATE_ID = "836534";

    //登录验证模板 ID
    public static final String SIGN_IN_TEMPLATE_ID = "742835";

    //注册验证模板 ID
    public static final String REGISTER_TEMPLATE_ID = "742832";

    //审核通过
    public static final String APROVED = "743951";

    //密码重置模板 ID
    public static final String PASSWORD_RESET_TEMPLATE_ID = "742461";

    //短信签名内容
    public static final String SIGN = "神州方圆";

    //短信SdkAppid
    public static final String SMS_SDK_APP_ID = "1400471475";

    //短信SdkAppid
    public static final String ENDPOINT = "sms.tencentcloudapi.com";

    // 存储验证码
    public static Map<String, String> local = new HashMap<>();




    /**
     * @param templateId   模板 ID
     * @param phoneNumbers 下发手机号码, 例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号
     * @return 验证码
     */
//    public static String sendSms(String templateId, String phoneNumbers) {
//        try {
//            Credential cred = new Credential(AuthenticationConstant.SECRET_ID, AuthenticationConstant.SECRET_KEY);
//
//            HttpProfile httpProfile = new HttpProfile();
//            httpProfile.setEndpoint(ENDPOINT);
//
//            ClientProfile clientProfile = new ClientProfile();
//            clientProfile.setHttpProfile(httpProfile);
//
//            SmsClient client = new SmsClient(cred, "", clientProfile);
//
//            SendSmsRequest req = new SendSmsRequest();
//            req.setPhoneNumberSet(new String[]{phoneNumbers});
//            req.setTemplateID(templateId);
//            req.setSign(SIGN);
//            //生成验证码
//            String param = String.valueOf((int) (Math.random() * 900000 + 100000));
//            if (StringUtils.equals(templateId, SMS_TEMPLATE_ID)) {
//                // 注册登录，5分钟有效
//                req.setTemplateParamSet(new String[]{param, "5"});
//            } else {
//                req.setTemplateParamSet(new String[]{param});
//            }
//            req.setSmsSdkAppid(SMS_SDK_APP_ID);
//            SendSmsResponse resp = client.SendSms(req);
//            SendStatus[] sendStatusSet = resp.getSendStatusSet();
//
//            if (StringUtils.equals(sendStatusSet[0].getCode(), "Ok")) {
//                local.put(resp.getRequestId(), param);
//                log.info("@@手机号 {} 发送短信验证码：{} smsId:{}",phoneNumbers,param,resp.getRequestId());
//                // 返回验证码id
//                return resp.getRequestId();
//            }
//            log.error(sendStatusSet[0].getMessage());
//        } catch (TencentCloudSDKException e) {
//            log.error("短信发送失败:{}", e);
//        }
//        return null;
//    }


    /**
     * @param param       内容
     * @param phoneNumber 下发手机号码, 例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号
     * @return 验证码
     */
    public static boolean sendSmsAprove(String param, String phoneNumber) {
        try {
            Credential cred = new Credential("AKIDA4hU2JDJuFKAPfcy15tQjR8h1RZXll4A", "0mZbQj38ywUnTwA8QxJ6mbEenNisocsw");

            HttpProfile httpProfile = new HttpProfile();

            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86" + phoneNumber};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setTemplateID(SMS_TEMPLATE_ID);
            req.setSign(SIGN);
            req.setSmsSdkAppid(SMS_SDK_APP_ID);
            req.setTemplateParamSet(new String[]{param});

            SendSmsResponse resp = client.SendSms(req);


            System.out.println("通知短信发送结果：{1}"+SendSmsResponse.toJsonString(resp));
            return true;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return false;
        }
    }




    public static void main(String[] args) {
        sendSmsAprove("123","18629346202");
    }
}

