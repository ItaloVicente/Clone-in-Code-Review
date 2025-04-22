		try {
			server.setUp();
		} catch (Exception e) {
			e.printStackTrace();
			Throwable why = null;
			if (e instanceof MultiException) {
				MultiException multi = (MultiException) e;
				List<Throwable> reasons = multi.getThrowables();
				why = reasons.get(0);
				assertTrue("Expected ServletException"
						why instanceof ServletException);
			} else if (e instanceof ServletException)
				why = e;

			if (why != null) {
				assertTrue("Wanted base-path"
						why.getMessage().contains("base-path"));
				return;
			}
		}
		fail("Expected ServletException complaining about unset base-path");
