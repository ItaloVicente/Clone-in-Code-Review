	@Test
	public void testPushRefUpdate() throws Exception {
		Git git = new Git(db);
		Git git2 = new Git(createBareRepository());

		final StoredConfig config = git.getRepository().getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(git2.getRepository().getDirectory().toURI()
				.toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		RevCommit commit = git.commit().setMessage("adding f").call();

		assertEquals(null
		git.push().setRemote("test").call();
		assertEquals(commit.getId()
				git2.getRepository().resolve("refs/heads/master"));

		git.branchCreate().setName("refs/heads/test").call();
		git.checkout().setName("refs/heads/test").call();


		for (int i = 0; i < 6; i++) {
			writeTrashFile("f" + i
			git.add().addFilepattern("f" + i).call();
			commit = git.commit().setMessage("adding f" + i).call();
			git.push().setRemote("test").call();
			git2.getRepository().getAllRefs();
			assertEquals("failed to update on attempt " + i
					git2.getRepository().resolve("refs/heads/test"));

		}
	}
