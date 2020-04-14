package com.projectfinalstartbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.projectfinalstartbot.function.DateTimes;
import com.projectfinalstartbot.service.ServiceWeb;

@Component
public class Controller {

    @Autowired
    private DateTimes dateTimes;

    @Autowired
    private ServiceWeb serviceWeb;
    
    @Scheduled(cron = "#{@cronExpression_1}") 
    public void runTask_1() {   
    	
        System.out.println(dateTimes.interDateTime() + " : start bot start");   
        serviceWeb.start();           
        System.out.println(dateTimes.interDateTime() + " : start bot stop");
    }
}
