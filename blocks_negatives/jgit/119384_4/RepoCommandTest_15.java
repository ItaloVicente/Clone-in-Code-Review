				.getRepository();
		File hello = new File(localDb.getWorkTree(), "Hello");
		assertTrue("The Hello file should exist", hello.exists());
		File foohello = new File(localDb.getWorkTree(), "foo/Hello");
		assertFalse("The foo/Hello file should be skipped", foohello.exists());
		localDb.close();
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("The Hello file should have expected content",
				"branch world", content);
