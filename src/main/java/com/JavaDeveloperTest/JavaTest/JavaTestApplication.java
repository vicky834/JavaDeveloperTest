package com.JavaDeveloperTest.JavaTest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
public class JavaTestApplication {

	public static void main(String[] args) {
		List<OrderPojo> list = new CopyOnWriteArrayList<>();
		AtomicInteger integer = new AtomicInteger(1);
		CSVFileParsing csvFileParsing = new CSVFileParsing(args[0],list,integer);

		JsonFileParsing jsonFileParsing = new JsonFileParsing(args[1],list,integer);

		ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(2);

		threadPoolExecutor.submit(csvFileParsing);
		threadPoolExecutor.submit(jsonFileParsing);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(list);
		System.out.println(json);
		threadPoolExecutor.shutdown();
	}

}
