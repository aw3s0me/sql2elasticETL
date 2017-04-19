package com.imergo.etl.processors;

import com.imergo.etl.beans.Medicine;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by korovin on 4/19/2017.
 */
public class MedicineESProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Medicine medicine = exchange.getIn().getBody(Medicine.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name", medicine.getName());
        map.put("pzn", medicine.getPzn());
        map.put("stoff", medicine.getStoff());

        exchange.getIn().setHeader("indexName", "medication");
        exchange.getIn().setHeader("indexType", "medicine");
        exchange.getIn().setBody(map);
    }
}
