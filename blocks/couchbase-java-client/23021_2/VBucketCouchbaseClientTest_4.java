		Integer i;
		for (i = 0; i < 10000; i++) {
			mc.set("test" + i, 0, i.toString());
		}
		mc.set("hello", 0, "world");
		String result = (String) mc.get("hello");
		assert (result.equals("world"));
