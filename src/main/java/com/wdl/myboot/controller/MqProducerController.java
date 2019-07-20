package com.wdl.myboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wdl.myboot.mq.producer.MqProducer;
import com.wdl.myboot.mq.entry.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class MqProducerController {
    @Autowired
    private MqProducer mqProducer;

    @RequestMapping(value = "/testProducer")
    @ResponseBody
    public String testProducer(@RequestParam(value = "key",required = false)String key,@RequestParam(value = "tag",required = false)String tag, @RequestParam(value = "value",required = false)String value, HttpServletResponse response){
        long start = System.currentTimeMillis();
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMsgId(UUID.randomUUID().toString());
        mqMessage.setMsgKey(key);
        mqMessage.setTopic("test_customerId");
        mqMessage.setTag(tag);
        mqMessage.setContent(JSON.toJSONString(value.replaceAll("\\r","").replaceAll("\\t","").replaceAll("\\n","")));
        boolean b = mqProducer.msgSend(mqMessage);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sendStatus",b?"OK":"FAIL");
        jsonObject.put("msgId",mqMessage.getMsgId());
        jsonObject.put("msg",mqMessage.getContent());
        response.setHeader("Access-Control-Allow-Origin","*");
        return jsonObject.toJSONString();
    }
}
