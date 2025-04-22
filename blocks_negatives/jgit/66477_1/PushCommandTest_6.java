		Git git = new Git(db);
		Git git2 = new Git(createBareRepository());

		final StoredConfig config = git.getRepository().getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config, "test");
		URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
				.toURL());
		remoteConfig.addURI(uri);
		remoteConfig.addPushRefSpec(new RefSpec("+refs/heads/*:refs/heads/*"));
		remoteConfig.update(config);
		config.save();

		writeTrashFile("f", "content of f");
		git.add().addFilepattern("f").call();
		RevCommit commit = git.commit().setMessage("adding f").call();

		assertEquals(null, git2.getRepository().resolve("refs/heads/master"));
		git.push().setRemote("test").call();
		assertEquals(commit.getId(),
				git2.getRepository().resolve("refs/heads/master"));

		git.branchCreate().setName("refs/heads/test").call();
		git.checkout().setName("refs/heads/test").call();


		for (int i = 0; i < 6; i++) {
			writeTrashFile("f" + i, "content of f" + i);
			git.add().addFilepattern("f" + i).call();
			commit = git.commit().setMessage("adding f" + i).call();
