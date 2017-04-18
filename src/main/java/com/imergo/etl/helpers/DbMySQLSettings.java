package com.imergo.etl.helpers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by korovin on 4/18/2017.
 */
public class DbMySQLSettings {
    private String jdbcDriver;
    private String dbAddress;
    private String userName;
    private String password;
    private String dbName;

    public DbMySQLSettings(String cfgPath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new InputStreamReader(ResourceHelper.getResource(
                cfgPath, this.getClass().getClassLoader())));

        JSONObject jsonObject = (JSONObject) obj;

        jdbcDriver = (String) jsonObject.get("driver");
        String host = (String) jsonObject.get("host");
        Long port = (Long)jsonObject.get("port");
        dbAddress = String.format("jdbc:mysql://%1$s:%2$s/", host, port);
        userName = (String) jsonObject.get("username");
        password = (String) jsonObject.get("password");
        dbName = (String) jsonObject.get("db");
    }

    public String getConnectionString() {
        return this.dbAddress + this.dbName;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
