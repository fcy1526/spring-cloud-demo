package org.example.logservice.service;

import org.example.logservice.dao.SysLogDao;
import org.example.logservice.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogService {

    @Autowired
    SysLogDao sysLogDao;

    public void saveLogger(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }
}
