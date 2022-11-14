package com.JavaDeveloperTest.JavaTest;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonFileParsing implements Runnable
{
    private String filename;
    List<OrderPojo> list;

    AtomicInteger integer;

    public JsonFileParsing(String filename, List<OrderPojo> list, AtomicInteger integer){
        this.filename = filename;
        this.list = list;
        this.integer = integer;
    }

    @Override public void run()
    {
        Object obj = null;
        try
        {
            obj = new JSONParser().parse(new FileReader(filename));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
        JSONArray array = (JSONArray) obj;
        OrderPojo order;
        int i = 0;
        for (Object jsonObject : array)
        {
            order = new OrderPojo();
            try
            {
                JSONObject object1 = (JSONObject) jsonObject;
                order.orderId = Integer.parseInt(String.valueOf((Long) object1.get("orderId")));
                order.amount = (Number) object1.get("amount");
                order.currency = (String) object1.get("currency");
                order.comment = (String) object1.get("comment");
                order.line = ++i;
                order.filename = filename;
                order.result = "OK";
                order.id = integer.getAndIncrement();
            }catch (Exception e){
                order = new OrderPojo();
                order.result ="Error Occurs" + e.getMessage() + " InCorrect input : Please Check line number " + ++i + " of input file" + filename;
            }
            list.add(order);
        }
    }
}
