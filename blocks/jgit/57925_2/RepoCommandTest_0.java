					.getRepository();) {
				File gitmodules = new File(localDb.getWorkTree()
						".gitmodules");
				assertTrue("The .gitmodules file should exist"
						gitmodules.exists());
				FileBasedConfig c = new FileBasedConfig(gitmodules
						FS.DETECTED);
				c.load();
				assertEquals("standard branches work"
						c.getString("submodule"
				assertEquals("long branches work"
						c.getString("submodule"
			}
