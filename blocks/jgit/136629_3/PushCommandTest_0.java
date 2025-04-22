	@Test
	public void testPushDefaultDetachedHead() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
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
			git.checkout().setName(commit.getName()).call();
			String head = git.getRepository().getFullBranch();
			assertTrue(ObjectId.isId(head));
			assertEquals(commit.getName()
			assertThrows(DetachedHeadException.class
					() -> git.push().setRemote("test").call());
		}
	}

	@Test
	public void testPushDefaultNothing() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
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

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			assertThrows(InvalidRefNameException.class
					() -> git.push().setRemote("test")
							.setPushDefault(PushDefault.NOTHING).call());
		}
	}

	@Test
	public void testPushDefaultMatching() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
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

			git.checkout().setName("also-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/also-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			git.push().setRemote("test").setPushDefault(PushDefault.MATCHING)
					.call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/also-pushed"));
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/master"));
			assertEquals(commit.getId()
					.resolve("refs/remotes/origin/branchtopush"));
			assertEquals(commit.getId()
					.resolve("refs/remotes/origin/also-pushed"));
			assertEquals(commit.getId()
					git.getRepository().resolve("refs/remotes/origin/master"));
		}
	}

	@Test
	public void testPushDefaultUpstream() throws Exception {
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
					"refs/heads/upstreambranch");
			config.save();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/upstreambranch"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			git.push().setRemote("test").setPushDefault(PushDefault.UPSTREAM)
					.call();
			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/upstreambranch"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			assertEquals(commit.getId()
					.resolve("refs/remotes/origin/upstreambranch"));
			assertEquals(null
					.resolve("refs/remotes/origin/branchtopush"));
		}
	}

	@Test
	public void testPushDefaultUpstreamNoTracking() throws Exception {
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

			assertThrows(InvalidRefNameException.class
					() -> git.push().setRemote("test")
							.setPushDefault(PushDefault.UPSTREAM).call());
		}
	}

	@Test
	public void testPushDefaultSimple() throws Exception {
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
					"refs/heads/branchtopush");
			config.save();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			git.push().setRemote("test").setPushDefault(PushDefault.SIMPLE)
					.call();
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

	@Test
	public void testPushDefaultSimpleNoTracking() throws Exception {
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

			assertThrows(InvalidRefNameException.class
					() -> git.push().setRemote("test")
							.setPushDefault(PushDefault.SIMPLE).call());
		}
	}

	@Test
	public void testPushDefaultSimpleDifferentTracking() throws Exception {
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
					"refs/heads/upstreambranch");
			config.save();

			assertThrows(InvalidRefNameException.class
					() -> git.push().setRemote("test")
							.setPushDefault(PushDefault.SIMPLE).call());
		}
	}

	@Test
	public void testPushDefaultFromConfig() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			URIish uri = new URIish(
					git2.getRepository().getDirectory().toURI().toURL());
			remoteConfig.addURI(uri);
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.setString("push"
			config.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			config = git.getRepository().getConfig();
			config.setString("branch"
					"refs/heads/upstreambranch");
			config.save();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/upstreambranch"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			PushCommand cmd = git.push();
			cmd.setRemote("test").setPushDefault(null).call();
			assertEquals(PushDefault.UPSTREAM
			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/upstreambranch"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			assertEquals(commit.getId()
					.resolve("refs/remotes/origin/upstreambranch"));
			assertEquals(null
					.resolve("refs/remotes/origin/branchtopush"));
		}
	}

	@Test
	public void testPushDefaultFromConfigDefault() throws Exception {
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
					"refs/heads/branchtopush");
			config.save();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			PushCommand cmd = git.push();
			cmd.setRemote("test").setPushDefault(null).call();
			assertEquals(PushDefault.SIMPLE
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

