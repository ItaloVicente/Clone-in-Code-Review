		try {
			server.setUp();
		} catch (Exception e) {
			assertTrue("Caught MultiException"
			MultiException multi = (MultiException) e;
			List<Throwable> reasons = multi.getThrowables();
			Throwable why = reasons.get(0);
			assertTrue("Caught ServletException"
					why instanceof ServletException);
			assertTrue("Wanted base-path"
					why.getMessage().contains("base-path"));
			return;
		}
		fail("Expected ServletException wrapped in MultiException"
				+ " complaining about unset base-path");
