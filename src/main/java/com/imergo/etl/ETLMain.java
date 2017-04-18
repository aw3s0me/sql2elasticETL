package com.imergo.etl;

import com.imergo.etl.errors.ApplicationErrorException;
import com.imergo.etl.helpers.DbMySQLSettings;
import com.imergo.etl.helpers.ResourceHelper;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by korovin on 4/18/2017.
 */
public class ETLMain {
    private final static String SQL_SETTINGS_PATH = "settings/db.settings.json";
    private final static String ETL_SCRIPT_SQL_PATH = "sql/etl.sql";
    private final static Logger LOG = LoggerFactory.getLogger(ETLMain.class);

    private String getSQL() {
        try {
            return IOUtils.toString(ResourceHelper.getResource(
                    ETL_SCRIPT_SQL_PATH, this.getClass().getClassLoader()), "UTF-8");
        } catch (IOException e) {
            LOG.error("Exception when trying to read sql script: " + e.getMessage());
            throw new ApplicationErrorException(e.getMessage());
        }
    }

    private DbMySQLSettings getSettings() {
        try {
            return new DbMySQLSettings(SQL_SETTINGS_PATH);
        } catch (IOException | ParseException e) {
            LOG.error("Could not read settings file: " + e.getMessage());
            throw new ApplicationErrorException(e.getMessage());
        }
    }

    private void process() {
        DbMySQLSettings settings = this.getSettings();
        String sql = this.getSQL();


    }

    public static void main(String[] args) {
        ETLMain app = new ETLMain();
    }
}