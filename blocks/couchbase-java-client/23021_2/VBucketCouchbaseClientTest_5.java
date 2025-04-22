		for (i = 0; i < 10000; i++) {
			String res = (String) mc.get("test" + i);
			assert (res.equals(i.toString()));
		}

		mc.shutdown(3, TimeUnit.SECONDS);
	}
