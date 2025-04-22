
	@Test
	public void testPushTagEndingWithAtSymbol() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		Git git1 = new Git(db);
		RevCommit commit = git1.commit().setMessage("initial commit").call();
		Ref tagRef = git1.tag().setName("tag@").call();

		try {
			db2.resolve(commit.getId().getName() + "^{commit}");
			fail("id shouldn't exist yet");
		} catch (MissingObjectException e) {
		}

		RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
		git1.push().setRemote("test").setRefSpecs(spec)
				.setPushTags()
				.call();

		assertEquals(commit.getId()
				db2.resolve(commit.getId().getName() + "^{commit}"));
		assertEquals(tagRef.getObjectId()
				db2.resolve(tagRef.getObjectId().getName()));
	}
