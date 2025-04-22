	@Test
	public void testPrePushHook() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		File hookOutput = new File(getTemporaryDirectory()
		writeHookFile(PrePushHook.NAME
				+ hookOutput.toPath() + "\"\ncat - >>\"" + hookOutput.toPath()
				+ "\"\nexit 0");

		Git git1 = new Git(db);
		RevCommit commit = git1.commit().setMessage("initial commit").call();

		RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
		git1.push().setRemote("test").setRefSpecs(spec).call();
		assertEquals(
				"1:test
						+ "/
						+ " refs/heads/x " + ObjectId.zeroId().name()
				read(hookOutput));
	}

	private File writeHookFile(final String name
			throws IOException {
		File path = new File(db.getWorkTree() + "/.git/hooks/"
		JGitTestUtil.write(path
		FS.DETECTED.setExecute(path
		return path;
	}


