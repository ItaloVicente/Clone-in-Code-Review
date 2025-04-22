		File hello = new File(db.getWorkTree(), "foo/hello.txt");
		assertTrue("submodule was checked out.", hello.exists());
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("submodule content is as expected.", "world", content);
