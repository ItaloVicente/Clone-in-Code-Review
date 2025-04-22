	public void testLsRemote() throws Exception {
		File directory = createTempDirectory("testRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setCloneAllBranches(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());


		LsRemoteCommand lsRemoteCommand = git2.lsRemote();
		Collection<Ref> refs = lsRemoteCommand.call();
		assertNotNull(refs);
		assertEquals(6
