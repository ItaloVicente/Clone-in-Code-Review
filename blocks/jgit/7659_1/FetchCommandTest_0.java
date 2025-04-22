	@Test
	public void testFetchBadRefs() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();
		Git git2 = new Git(db2);

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		git2.commit().setMessage("initial commit").call();
		git2.tag().setName("tag").call();
		File o = new File(git2.getRepository().getDirectory()
		File n = new File(git2.getRepository().getDirectory()
				"refs/tags/tag name");
		FileUtils.renameFile(o
		Git git1 = new Git(db);

		RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
		try {
			git1.fetch().setRemote("test").setRefSpecs(spec).call();
			fail("Should not be able to fetch from the bad repository");
		} catch (TransportException e) {
		}
	}
