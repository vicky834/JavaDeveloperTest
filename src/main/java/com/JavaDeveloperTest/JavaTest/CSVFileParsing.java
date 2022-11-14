package com.JavaDeveloperTest.JavaTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CSVFileParsing implements Runnable
{
    private String filename;
    List<OrderPojo> list;

    AtomicInteger atomicInteger;

    public CSVFileParsing(String filename, List<OrderPojo> list,AtomicInteger atomicInteger){
        this.filename = filename;
        this.list = list;
        this.atomicInteger = atomicInteger;
    }

    @Override public void run()
    {
        BufferedReader br;
        OrderPojo order;
        try
        {
            br = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException e)
        {
            order = new OrderPojo();
            order.result = e.getMessage();
            list.add(order);
            return;
        }
        String line = "";
        int i=0;
        while (true)   //returns a Boolean value
        {
            order = new OrderPojo();
            try
            {
                line = br.readLine();
                if(line==null)break;
                String[] data = line.split(",");
                order.orderId = Integer.parseInt(data[0]);
                order.amount = Integer.parseInt(data[1]);
                order.currency = data[2];
                order.comment = data[3];
                order.line = ++i;
                order.filename = filename;
                order.result = "OK";
                order.id = atomicInteger.getAndIncrement();
            }catch (Exception e){
                order = new OrderPojo();
                order.result = "Error Occurs" + e.getMessage() + " InCorrect input : Please Check line number " + ++i + " of input file" + filename;
            }
            list.add(order);
        }
    }
}
