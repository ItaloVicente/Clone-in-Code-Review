	@Test
	public void testBranchPushRemote() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(
					git2.getRepository().getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.setString("remote"
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			config = git.getRepository().getConfig();
			config.setString("branch"
			config.setString("branch"
			config.setString("branch"
					"refs/heads/branchtopush");
			config.save();

			assertThrows(InvalidRefNameException.class
					.setPushDefault(PushDefault.UPSTREAM).call());
		}
	}

	@Test
	public void testRemotePushDefault() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(
					git2.getRepository().getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.setString("remote"
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			config = git.getRepository().getConfig();
			config.setString("branch"
			config.setString("branch"
					"refs/heads/branchtopush");
			config.save();

			assertThrows(InvalidRefNameException.class
					.setPushDefault(PushDefault.UPSTREAM).call());
		}
	}

	@Test
	public void testDefaultRemote() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(
					git2.getRepository().getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			config = git.getRepository().getConfig();
			config.setString("branch"
					"refs/heads/branchtopush");
			config.save();

			PushCommand cmd = git.push().setPushDefault(PushDefault.UPSTREAM);
			TransportException e = assertThrows(TransportException.class
					() -> cmd.call());
			assertEquals(NoRemoteRepositoryException.class
					e.getCause().getClass());
			assertEquals("origin"
		}
	}

	@Test
	public void testDefaultPush() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(
					git2.getRepository().getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			config = git.getRepository().getConfig();
			config.setString("branch"
			config.save();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			PushCommand cmd = git.push();
			cmd.call();
			assertEquals("test"
			assertEquals(PushDefault.CURRENT
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			assertEquals(commit.getId()
					.resolve("refs/remotes/origin/branchtopush"));
		}
	}

