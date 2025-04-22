		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				PrintStream out = new PrintStream(bytes
						StandardCharsets.UTF_8);
				PrintStream err = new PrintStream(NullOutputStream.INSTANCE)) {
			MockPrePushHook hook = new MockPrePushHook(db
			executePush(hook);
			assertEquals(Status.OK
			assertTrue(rruOk.isFastForward());
			assertEquals(Status.NON_EXISTING
			out.flush();
			String result = new String(bytes.toString(StandardCharsets.UTF_8));
			assertEquals(
					"null 0000000000000000000000000000000000000000 "
							+ "refs/heads/master 2c349335b7f797072cf729c4f3bb0914ecb6dec9\n"
					result);
		}
