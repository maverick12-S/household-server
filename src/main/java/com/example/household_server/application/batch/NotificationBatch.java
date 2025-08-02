package com.example.household_server.application.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.household_server.application.service.MailLogicService;

@Component
public class NotificationBatch {

    @Autowired
    private MailLogicService mailLogic;

    @Scheduled(cron="* * * * * *")
    public void doSend(){
    mailLogic.sendDailySummaryEmails();
    }

}
