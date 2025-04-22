	@Test
	public void testCloneRepositoryWithBranchShortName() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("test");
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals("refs/heads/test"
	}

	@Test
	public void testCloneRepositoryWithTagName() throws Exception {
		File directory = createTempDirectory("testCloneRepositoryWithBranch");
		CloneCommand command = Git.cloneRepository();
		command.setBranch("tag-initial");
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		ObjectId taggedCommit = db.resolve("tag-initial^{commit}");
		assertEquals(taggedCommit.name()
				.getRepository().getFullBranch());
	}

