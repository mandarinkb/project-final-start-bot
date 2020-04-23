package com.projectfinalstartbot.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectfinalstartbot.dao.Database;
import com.projectfinalstartbot.dao.Redis;
import com.projectfinalstartbot.function.Elasticsearch;
import com.projectfinalstartbot.function.Query;
import com.projectfinalstartbot.function.SwitchDatabase;

import redis.clients.jedis.Jedis;

@Service
public class ServiceWebImpl implements ServiceWeb {
	@Autowired
	private Query q;
	
    @Autowired
    private SwitchDatabase swdb;
    
	@Autowired
	private Elasticsearch elas;

    @Autowired
    private Database db;

    @Autowired
    private Redis rd;       

    @Override
    public void start() {
        JSONObject json = new JSONObject();
        Jedis redis = rd.connect();
        
        //clear old redis
        redis.del("startUrl");   
        redis.del("categoryUrl"); 
        redis.del("detailUrl"); 
        
        // select database working and clear data in elasticsearch
        String dbName = q.StrExcuteQuery("select DATABASE_NAME from SWITCH_DATABASE where DATABASE_STATUS = '1' ");
        elas.deleteIndex(dbName);
        
        //switch database
        swdb.switchdb();
        
        String sql = "select * from WEB where WEB_STATUS = 1";
        try {
            Connection conn = db.connectDatase();
            ResultSet result = db.getResultSet(conn, sql);
            while (result.next()) {
                json.put("web_id", result.getInt("Web_ID"));
                json.put("web_name", result.getString("WEB_NAME"));
                json.put("url", result.getString("WEB_URL"));
                json.put("web_status", result.getString("WEB_STATUS"));
                json.put("icon_url", result.getString("ICON_URL"));
                redis.rpush("startUrl", json.toString());
            }
            conn.close();
            redis.close();
        } catch (SQLException | JSONException e) {
            e.getMessage();
        }     
    }
}
