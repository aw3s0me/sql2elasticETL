package com.imergo.etl.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

/**
 * Created by korovin on 4/18/2017.
 */
public class MedicineProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map row = exchange.getIn().getBody(Map.class);
        System.out.println("Processing " + row);

        System.out.println((String) row.get("PZN"));
        System.out.println((String) row.get("NAME"));
        System.out.println((String) row.get("STOFF"));

        // Medicine medicine = new Medicine();

        exchange.getOut().setBody("ololo");
    }
}
