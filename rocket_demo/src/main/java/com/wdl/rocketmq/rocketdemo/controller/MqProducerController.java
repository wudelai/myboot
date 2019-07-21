package com.wdl.rocketmq.rocketdemo.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.wdl.rocketmq.rocketdemo.producer.MqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@Slf4j
@RestController
public class MqProducerController {
    @Autowired
    private MqProducer mqProducer;

    @RequestMapping(value = "/testProducer")
    public JSONObject testProducer(@RequestParam(value = "key",required = false)String key,@RequestParam(value = "tag",required = false)String tag, @RequestParam(value = "value",required = false)String value, HttpServletResponse response){
        long start = System.currentTimeMillis();
        SendResult b = mqProducer.sendMsg(null,tag,key,JSON.toJSONString(value.replaceAll("\\r","").replaceAll("\\t","").replaceAll("\\n","")));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sendStatus",b.getSendStatus());
        jsonObject.put("msgId",b.getMsgId());
        jsonObject.put("msg",JSON.toJSONString(value.replaceAll("\\r","").replaceAll("\\t","").replaceAll("\\n","")));
        response.setHeader("Access-Control-Allow-Origin","*");
        return jsonObject;
    }
}
