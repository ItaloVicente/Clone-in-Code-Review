	}

	@Test
	public void testPushExistingBranchesWithoutPushRefSpec() throws Exception {
		Git git = new Git(db);
		Git git2 = new Git(createBareRepository());

		final StoredConfig config = git.getRepository().getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
				.toURL());
		remoteConfig.addURI(uri);
		remoteConfig.addFetchRefSpec(new RefSpec(
		remoteConfig.update(config);
		config.save();

		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit0 = git.commit().setMessage("adding f").call();

		git.checkout().setName("branch1").setCreateBranch(true).call();
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit1 = git.commit().setMessage("adding f").call();

		git.checkout().setName("branch2").setCreateBranch(true).call();
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit2 = git.commit().setMessage("adding f").call();

		git.checkout().setName(Constants.MASTER).call();

		assertEquals(null
		assertEquals(null
		assertEquals(null
		git.push().setRemote("test").setPushAll().call();
		assertEquals(commit1.getId()
				git2.getRepository().resolve("refs/heads/branch1"));
		assertEquals(commit2.getId()
				git2.getRepository().resolve("refs/heads/branch2"));
		assertEquals(commit0.getId()
				git2.getRepository().resolve("refs/heads/master"));

		git.checkout().setName("branch1").call();
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit1a = git.commit().setMessage("changing f").call();

		git.checkout().setName("branch2").call();
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit2a = git.commit().setMessage("changing f").call();

		git.checkout().setName(Constants.MASTER).call();

		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit0a = git.commit().setMessage("changing f").call();

		git.push().setRemote("test").call();
		assertEquals(commit0a.getId()
				git2.getRepository().resolve("refs/heads/master"));
		assertEquals(commit1a.getId()
				git2.getRepository().resolve("refs/heads/branch1"));
		assertEquals(commit2a.getId()
				git2.getRepository().resolve("refs/heads/branch2"));
