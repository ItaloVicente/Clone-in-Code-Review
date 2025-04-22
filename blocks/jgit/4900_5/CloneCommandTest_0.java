
	@Test
	public void testCloneRepositoryWithSubmodules() throws Exception {
		git.checkout().setName(Constants.MASTER).call();

		String file = "file.txt";
		writeTrashFile(file
		git.add().addFilepattern(file).call();
		RevCommit commit = git.commit().setMessage("create file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		git.add().addFilepattern(path)
				.addFilepattern(Constants.DOT_GIT_MODULES).call();
		git.commit().setMessage("adding submodule").call();

		File directory = createTempDirectory("testCloneRepositoryWithSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(true);
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER
		assertTrue(new File(git2.getRepository().getWorkTree()
				+ File.separatorChar + file).exists());

		SubmoduleStatusCommand status = new SubmoduleStatusCommand(
				git2.getRepository());
		Map<String
		SubmoduleStatus pathStatus = statuses.get(path);
		assertNotNull(pathStatus);
		assertEquals(SubmoduleStatusType.INITIALIZED
		assertEquals(commit
		assertEquals(commit
	}
