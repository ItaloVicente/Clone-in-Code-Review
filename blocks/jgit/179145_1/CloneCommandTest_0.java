	@Test
	public void testCloneEmptyRepository() throws Exception {
		db = createWorkRepository();
		trash = db.getWorkTree();
		git = new Git(db);

		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository().setDirectory(directory)
				.setURI(fileUri());
		Git git2 = command.call();
		Repository clonedRepo = git2.getRepository();
		addRepoToClose(clonedRepo);

		Map<AnyObjectId
				.getAllRefsByPeeledObjectId();
		assertTrue(allRefs.isEmpty());
		Ref head = clonedRepo.exactRef(Constants.HEAD);
		assertNull(head.getObjectId());
	}

