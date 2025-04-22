	@Test
	public void testPrePushHookRedirects() throws JGitInternalException
			IOException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		writeHookFile(PrePushHook.NAME
				+ "echo \"1:$1
				+ "exit 0\n");

		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit")
					.call();

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			try (ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
					ByteArrayOutputStream errBytes = new ByteArrayOutputStream();
					PrintStream stdout = new PrintStream(outBytes
							StandardCharsets.UTF_8);
					PrintStream stderr = new PrintStream(errBytes
							StandardCharsets.UTF_8)) {
				git1.push()
						.setRemote("test")
						.setRefSpecs(spec)
						.setHookOutputStream(stdout)
						.setHookErrorStream(stderr)
						.call();
				String out = outBytes.toString(StandardCharsets.UTF_8);
				String err = errBytes.toString(StandardCharsets.UTF_8);
				assertEquals("1:test
				assertEquals("refs/heads/master " + commit.getName()
						+ " refs/heads/x " + ObjectId.zeroId().name() + '\n'
						err);
			}
		}
	}

