				.getRepository();
		File gitmodules = new File(localDb.getWorkTree(), ".gitmodules");
		assertTrue("The .gitmodules file should exist", gitmodules.exists());
		BufferedReader reader = new BufferedReader(new FileReader(gitmodules));
		String content = reader.readLine();
		reader.close();
		assertEquals("The first line of .gitmodules file should be as expected",
				"[submodule \"foo\"]", content);
		String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
		localDb.close();
		String remote = defaultDb.resolve(Constants.HEAD).name();
		assertEquals("The gitlink should be the same as remote head", remote,
				gitlink);
