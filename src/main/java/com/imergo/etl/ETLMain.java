package com.imergo.etl;

import com.imergo.etl.errors.ApplicationErrorException;
import com.imergo.etl.helpers.DbMySQLSettings;
import com.imergo.etl.helpers.ResourceHelper;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jdbc.JdbcComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.commons.dbcp.BasicDataSource;
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
    private final static String SQL_FROM_ENDPOINT_URI = "direct:select";
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

    private SimpleRegistry createRegistry(DbMySQLSettings sqlSettings) {
        SimpleRegistry reg = new SimpleRegistry();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(sqlSettings.getJdbcDriver());
        ds.setUsername(sqlSettings.getUserName());
        ds.setPassword(sqlSettings.getPassword());
        ds.setUrl(sqlSettings.getConnectionString());
        reg.put("abdata", ds);
        return reg;
    }

    private String ES_TO_ENDPOINT_URI = "elasticsearch://indexer?operation=BULK_INDEX&ip=127.0.0.1&port=9300";

    private void process() throws Exception {
        DbMySQLSettings settings = this.getSettings();
        SimpleRegistry registry = this.createRegistry(settings);
        String sql = this.getSQL();

        CamelContext camelContext = new DefaultCamelContext(registry);
        ProducerTemplate template = camelContext.createProducerTemplate();
        camelContext.addComponent("jdbc", new JdbcComponent(camelContext));
        camelContext.getShutdownStrategy().setTimeout(5L);
        camelContext.addRoutes(new MedicineRouteBuilder(camelContext, SQL_FROM_ENDPOINT_URI, sql));

        camelContext.start();

        Endpoint endpoint = camelContext.getEndpoint(SQL_FROM_ENDPOINT_URI);
        template.setDefaultEndpoint(endpoint);
        template.sendBody("direct:select");
    }

    public static void main(String[] args) {
        ETLMain app = new ETLMain();
        try {
            app.process();
        } catch (Exception e) {
            LOG.error("Could not perform route processing: " + e.getMessage());
        }
    }
}