package com.projectfinalstartbot.function;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component
public class Elasticsearch {
    @Value("${elasticsearch_ip}")
    private String elasticsearch_ip;

    
    public void deleteIndex(String index) {
        try {
            HttpResponse<String> response = Unirest.delete(elasticsearch_ip+index)
                    .header("Accept", "*/*")
                    .header("cache-control", "no-cache")
                    .asString();
        } catch (UnirestException ex) {
            Logger.getLogger(Elasticsearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
