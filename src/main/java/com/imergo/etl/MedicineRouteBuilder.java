package com.imergo.etl;

import com.imergo.etl.helpers.DbMySQLSettings;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * Created by korovin on 4/18/2017.
 */
public class MedicineRouteBuilder extends RouteBuilder {
    private final CamelContext ctx;
    private String SQL_FROM_ENDPOINT_URI = "direct:select";
    private String ES_TO_ENDPOINT_URI = "elasticsearch://indexer?operation=BULK_INDEX&ip=127.0.0.1&port=9300";

    private Endpoint createEndpoint(String sql) {
        Endpoint endpoint = this.ctx.getEndpoint(SQL_FROM_ENDPOINT_URI);
        Exchange exchange = endpoint.createExchange();

        exchange.getIn().setBody(constant(sql));
        // Exchange out = new
        return null;
    }

    private JndiRegistry createRegistry(DbMySQLSettings sqlSettings) {
        JndiRegistry reg = new JndiRegistry();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(sqlSettings.getJdbcDriver());
        ds.setUsername(sqlSettings.getUserName());
        ds.setPassword(sqlSettings.getPassword());
        ds.setUrl(sqlSettings.getConnectionString());
        reg.bind("abdata", ds);
        return reg;
    }

    public MedicineRouteBuilder(CamelContext context, DbMySQLSettings sqlSettings, String sql) throws Exception {
        super(context);
        this.ctx = context;
    }
    
    @Override
    public void configure() throws Exception {
        // TODO: configure route
        from(SQL_FROM_ENDPOINT_URI)
            .to("jdbc:abdata?readSize=10");
    }
}
