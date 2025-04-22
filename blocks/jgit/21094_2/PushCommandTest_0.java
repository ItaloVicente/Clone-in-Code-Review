
	@Test
	public void testPushAfterGC() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		Git git1 = new Git(db);
		Git git2 = new Git(db2);

		RevCommit commit = git1.commit().setMessage("initial commit").call();

		git1.branchCreate().setName("refs/heads/test").call();
		git1.push().setRemote("test").setRefSpecs(spec)
				.call();

		git2.checkout().setName("refs/heads/test").call();

		writeTrashFile("a"
		git2.add().addFilepattern("a").call();
		RevCommit commit2 = git2.commit().setMessage("adding a").call();

		Properties res = git1.gc().setExpire(null).call();
		assertEquals(7

		writeTrashFile("b"
		git1.add().addFilepattern("b").call();
		RevCommit commit3 = git1.commit().setMessage("adding b").call();

		try {
			git1.push().setRemote("test").setRefSpecs(spec)
					.call();
		} catch (Exception e) {
			fail("caught MissingObjectException for a change we don't have");
		}

		try {
			db.resolve(commit2.getId().getName() + "^{commit}");
			fail("id shouldn't exist locally");
		} catch (MissingObjectException e) {
		}
		assertEquals(commit2.getId()
				db2.resolve(commit2.getId().getName() + "^{commit}"));
		assertEquals(commit3.getId()
				db2.resolve(commit3.getId().getName() + "^{commit}"));
	}
