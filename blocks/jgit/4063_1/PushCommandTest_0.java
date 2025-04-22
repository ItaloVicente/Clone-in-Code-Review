	@Test
	public void testPushToCurrentBranch() throws Exception {
		Git git = new Git(db);
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		git.commit().setMessage("adding f").call();

		final StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		config.save();

		File directory = createTempDirectory("testPushToCurrentBranch");
		Git git2 = Git
				.cloneRepository()
				.setDirectory(directory).call();
		assertNotNull(git2);
		addRepoToClose(git2.getRepository());

		File f = new File(git2.getRepository().getWorkTree()
		write(f
		git2.add().addFilepattern("f").call();
		git2.commit().setMessage("updating f").call();

		Iterable<PushResult> resultIterable = git2.push().call();

		PushResult result = resultIterable.iterator().next();
		RemoteRefUpdate remoteUpdate = result
				.getRemoteUpdate("refs/heads/master");
		assertEquals(
				org.eclipse.jgit.transport.RemoteRefUpdate.Status.REJECTED_OTHER_REASON
				remoteUpdate.getStatus());
