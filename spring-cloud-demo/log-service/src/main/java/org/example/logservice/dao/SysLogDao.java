package org.example.logservice.dao;

import org.example.logservice.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogDao extends JpaRepository<SysLog, Long> {
}
