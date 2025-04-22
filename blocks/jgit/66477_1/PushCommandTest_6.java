		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
					.toURL());
			remoteConfig.addURI(uri);
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			assertEquals(null
