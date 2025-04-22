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

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
			git.push().setRemote("test").setRefSpecs(
					new RefSpec("refs/heads/master:refs/heads/master")
					new RefSpec(
							"refs/heads/branchtopush:refs/heads/branchtopush"))
					.call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/master"));
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit bCommit = git.commit().setMessage("on branchtopush")
					.call();
			git.checkout().setName("master").call();
			writeTrashFile("m"
			git.add().addFilepattern("m").call();
			RevCommit mCommit = git.commit().setMessage("on master").call();
			Iterable<PushResult> result = git.push().setRemote("test")
					.setPushDefault(PushDefault.MATCHING)
					.call();
			int n = 0;
			for (PushResult r : result) {
				n++;
				assertEquals(1
				assertEquals(2
				for (RemoteRefUpdate update : r.getRemoteUpdates()) {
					assertFalse(update.isMatching());
					assertTrue(update.getSrcRef()
							.equals("refs/heads/branchtopush")
							|| update.getSrcRef().equals("refs/heads/master"));
					assertEquals(RemoteRefUpdate.Status.OK
				}
			}
			assertEquals(bCommit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(mCommit.getId()
					git2.getRepository().resolve("refs/heads/master"));
			assertEquals(bCommit.getId()
					.resolve("refs/remotes/origin/branchtopush"));
			assertEquals(null
					.resolve("refs/remotes/origin/not-pushed"));
			assertEquals(mCommit.getId()
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

			config = git.getRepository().getConfig();
			config.setString("branch"
			config.save();

			assertThrows(InvalidRefNameException.class
					() -> git.push().setRemote("test")
							.setPushDefault(PushDefault.UPSTREAM).call());
		}
	}

	@Test
	public void testPushDefaultUpstreamTriangular() throws Exception {
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
					"upstreambranch");
			config.save();

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
	public void testPushDefaultSimpleTriangular() throws Exception {
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
			git.push().setRemote("test").setPushDefault(PushDefault.SIMPLE)
					.call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/upstreambranch"));
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

			config = git.getRepository().getConfig();
			config.setString("branch"
			config.save();

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

