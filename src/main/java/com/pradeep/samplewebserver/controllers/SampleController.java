package com.pradeep.samplewebserver.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SampleController {

	private static final Map<Long, Object> map = new HashMap<>();
	private static final Map<Long, Object> syncMap = new HashMap<>();

	@GetMapping("/test/{id}")
	public void sampleRequest(@PathVariable Long id) {
		syncMap.putIfAbsent(id, id);
		synchronized (syncMap.get(id)) {
			if (!map.containsKey(id)) {
				map.putIfAbsent(id, new Object());
				System.out.println(id + " initial call start");
				// processing method call here
				System.out.println(id + " initial call end");
			}
			else {
				System.out.println(id + " subsequent call");
				// processing method call here
			}
		}
	}
}