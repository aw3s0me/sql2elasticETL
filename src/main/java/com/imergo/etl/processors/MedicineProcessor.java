package com.imergo.etl.processors;

import com.imergo.etl.beans.Medicine;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

/**
 * Created by korovin on 4/18/2017.
 */
public class MedicineProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("1");
        Map row = exchange.getIn().getBody(Map.class);
        System.out.println("Processing " + row);

//        System.out.println((String) row.get("PZN"));
//        System.out.println((String) row.get("NAME"));
//        System.out.println((String) row.get("STOFF"));
        String pzn = (String) row.get("PZN");
        String name = (String) row.get("NAME");
        String stoff = (String) row.get("STOFF");

        Medicine medicine = new Medicine(pzn, name, stoff);

        exchange.getOut().setBody(medicine);
    }
}
