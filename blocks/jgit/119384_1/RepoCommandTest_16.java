				.getRepository()) {
			File gitmodules = new File(localDb.getWorkTree()
			assertTrue("The .gitmodules file should exist"
					gitmodules.exists());
			try (BufferedReader reader = new BufferedReader(
					new FileReader(gitmodules))) {
				String content = reader.readLine();
				assertEquals(
						"The first line of .gitmodules file should be as expected"
						"[submodule \"foo\"]"
			}
			String gitlink = localDb.resolve(Constants.HEAD + ":foo").name();
			String remote = defaultDb.resolve(Constants.HEAD).name();
			assertEquals("The gitlink should be the same as remote head"
					remote
		}
