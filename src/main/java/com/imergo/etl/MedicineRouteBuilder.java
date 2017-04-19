package com.imergo.etl;

import com.imergo.etl.processors.MedicineESProcessor;
import com.imergo.etl.processors.MedicineSQLProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by korovin on 4/18/2017.
 */
public class MedicineRouteBuilder extends RouteBuilder {
    private final String sqlEndpoint;
    private final String sql;
    private final static String ES_START_ENDPOINT = "direct:es-start";
    private final static String ES_TO_ENDPOINT_URI = "elasticsearch://medicalcluster?operation=INDEX&ip=127.0.0.1&port=9300&indexName=medication&indexType=medicine";

    public MedicineRouteBuilder(CamelContext context, String sqlEndpoint, String sql) {
        super(context);
        this.sqlEndpoint = sqlEndpoint;
        this.sql = sql;
    }

    @Override
    public void configure() throws Exception {
        System.out.println(this.sql);
        System.out.println(this.sqlEndpoint);
        from(this.sqlEndpoint)
                .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                .log("select from abdata start")
                .setBody(constant(this.sql))
                .to("jdbc:abdata?readSize=10")
                .log("split rows")
                .split(body())
                .process(new MedicineSQLProcessor())
                .log("${body}")
                .to(ES_START_ENDPOINT);
        from(ES_START_ENDPOINT)
                .process(new MedicineESProcessor())
                .to(ES_TO_ENDPOINT_URI)
                .log("Uploaded medicine to Elastic search index");
    }
}
