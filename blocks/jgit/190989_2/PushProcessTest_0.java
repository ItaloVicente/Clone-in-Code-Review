		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				PrintStream out = new PrintStream(bytes
						StandardCharsets.UTF_8);
				PrintStream err = new PrintStream(NullOutputStream.INSTANCE)) {
			MockPrePushHook hook = new MockPrePushHook(db
			testOneUpdateStatus(rru
					hook);
			out.flush();
			String result = new String(bytes.toString(StandardCharsets.UTF_8));
			assertEquals(""
		}
