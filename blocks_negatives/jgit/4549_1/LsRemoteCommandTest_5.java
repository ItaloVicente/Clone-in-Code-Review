	public void testLsRemote() {
		try {
			File directory = createTempDirectory("testRepository");
			CloneCommand command = Git.cloneRepository();
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			command.setCloneAllBranches(true);
			Git git2 = command.call();
			addRepoToClose(git2.getRepository());


			LsRemoteCommand lsRemoteCommand = git2.lsRemote();
			Collection<Ref> refs = lsRemoteCommand.call();
			assertNotNull(refs);
			assertEquals(6, refs.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
