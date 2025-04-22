		server.setUp();

		List<RecordingLogger.Warning> events = RecordingLogger.getWarnings();
		assertFalse("Servlet started without base-path", events.isEmpty());

		Throwable why = events.get(0).getCause();
		assertTrue("Caught ServletException", why instanceof ServletException);
		assertTrue("Wanted base-path", why.getMessage().contains("base-path"));
