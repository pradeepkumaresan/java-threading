package com.pradeep.samplewebserver.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SampleController {

	private static Map<Long, CountDownLatch> map = new HashMap<>();

	@GetMapping("/test/{id}")
	public void sampleRequest(@PathVariable Long id) {
		if (!map.containsKey(id)) {
			map.putIfAbsent(id, new CountDownLatch(1));
			System.out.println(id + " first call start");
			//processing method call here
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(id + " first call end");
			map.get(id).countDown();
			
		}
		else {
			try {
				map.get(id).await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(id + " subsequent");
			//processing method call here
			
		}
		

	}

}
