package com.pradeep.samplewebserver.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SampleController {

	private static final Map<Long, CountDownLatch> map = new HashMap<>();
	private static final Map<Long, Object> syncMap = new HashMap<>();

	@GetMapping("/test/{id}")
	public void sampleRequest(@PathVariable Long id) {
		syncMap.putIfAbsent(id, id);
		synchronized (syncMap.get(id)) {

			if (!map.containsKey(id)) {
				map.putIfAbsent(id, new CountDownLatch(1));
				System.out.println(id + " initial call start");
				// processing method call here
				try {
					Thread.sleep(id * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(id + " initial call end");
				map.get(id).countDown();

			}
		}
		if (map.containsKey(id)) {
			System.out.println(id + " subsequent call start");
			try {
				map.get(id).await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(id + " subsequent call end");
			// processing method call here

		}

	}

}
