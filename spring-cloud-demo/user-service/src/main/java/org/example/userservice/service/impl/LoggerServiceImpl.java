package org.example.userservice.service.impl;

import com.alibaba.fastjson.JSON;
import org.example.userservice.bean.SysLog;
import org.example.userservice.config.RabbitConfig;
import org.example.userservice.service.LoggerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerServiceImpl implements LoggerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void log(SysLog sysLog) {
        amqpTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSONString(sysLog));
    }
}
