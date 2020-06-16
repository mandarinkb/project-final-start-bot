package com.projectfinalstartbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.projectfinalstartbot.function.DateTimes;
import com.projectfinalstartbot.function.Log;
import com.projectfinalstartbot.service.ServiceWeb;

@Component
public class Controller {
	@Autowired
	private Log log;

    @Autowired
    private DateTimes dateTimes;

    @Autowired
    private ServiceWeb serviceWeb;
    
    @Scheduled(cron = "#{@cronExpression_1}") 
    public void start() {   
    	
        System.out.println(dateTimes.interDateTime() + " : start bot start");   
        serviceWeb.start();           
        System.out.println(dateTimes.interDateTime() + " : start bot stop");
        log.createLog(dateTimes.datetime(),dateTimes.timestamp(), "system", "start web scrapping", "start web scrapping");
    }
}
