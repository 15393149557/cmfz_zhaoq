package com.baizhi.zq.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/

//简单版发送短信验证
public class SendSms {

    public static void getPhone() {

        //参数服务器地址：不需要改,自己的AK
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Frdf4ujUdW2jwj6rdEe", "Sd5qaangzGsZvZ1BEUfCSMjmEvO0CU");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15393149557,15122517887");  //手机号
        request.putQueryParameter("SignName", "小蛋黄");  //签名
        request.putQueryParameter("TemplateCode", "SMS_176936091"); //模板Id
        request.putQueryParameter("TemplateParam", "{\"code\":\"888888\"}"); //发送的 code
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getPhone();
    }
}
