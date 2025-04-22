		server.setUp();

		List<RecordingLogger.Warning> events = RecordingLogger.getWarnings();
		assertFalse("Servlet started without base-path"

		Throwable why = events.get(0).getCause();
		assertTrue("Caught ServletException"
		assertTrue("Wanted base-path"
