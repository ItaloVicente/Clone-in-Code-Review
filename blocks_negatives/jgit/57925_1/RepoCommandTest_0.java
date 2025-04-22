					.getRepository();
			File gitmodules = new File(localDb.getWorkTree(), ".gitmodules");
			assertTrue("The .gitmodules file should exist", gitmodules.exists());
			FileBasedConfig c = new FileBasedConfig(gitmodules, FS.DETECTED);
			c.load();
			assertEquals("standard branches work", "master",
				c.getString("submodule", "with-branch", "branch"));
			assertEquals("long branches work", "refs/heads/master",
				c.getString("submodule", "with-long-branch", "branch"));
