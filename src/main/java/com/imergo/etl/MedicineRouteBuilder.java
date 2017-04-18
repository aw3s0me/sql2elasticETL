package com.imergo.etl;

import com.imergo.etl.processors.MedicineProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by korovin on 4/18/2017.
 */
public class MedicineRouteBuilder extends RouteBuilder {
    private final String sqlEndpoint;
    private final String sql;

    public MedicineRouteBuilder(CamelContext context, String sqlEndpoint, String sql) {
        super(context);
        this.sqlEndpoint = sqlEndpoint;
        this.sql = sql;
    }

    @Override
    public void configure() throws Exception {
        System.out.println(this.sql);
        System.out.println(this.sqlEndpoint);
//        // TODO: add elastic search and parallelize sql transformation
        from(this.sqlEndpoint)
                .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                .log("select from abdata start")
                .setBody(constant(this.sql))
                .to("jdbc:abdata?readSize=10")
                .log("split rows")
                .split(body())
                .process(new MedicineProcessor())
                .log("${body}");

//        from(this.sqlEndpoint)
//                .process(new MedicineProcessor())
//                .setBody(constant(this.sql))
//                .to("jdbc:abdata?readSize=10")
//                .log("${body}");

//        from(this.sqlEndpoint)
//                .to("jdbc:abdata?readSize=10")
//                .split(body())
//                .to("mock:result");
    }
}
