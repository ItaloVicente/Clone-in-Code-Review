			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecommendShallow(true)
				.call();
			File directory = createTempDirectory("testBareRepo");
			try (Repository localDb = Git.cloneRepository()
					.setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();) {
				File gitmodules = new File(localDb.getWorkTree(),
						".gitmodules");
				assertTrue("The .gitmodules file should exist",
						gitmodules.exists());
				FileBasedConfig c = new FileBasedConfig(gitmodules,
						FS.DETECTED);
				c.load();
				assertEquals("Recording shallow configuration should work", "true",
						c.getString("submodule", "shallow-please", "shallow"));
				assertNull("Recording non shallow configuration should work",
						c.getString("submodule", "non-shallow", "shallow"));
			}
