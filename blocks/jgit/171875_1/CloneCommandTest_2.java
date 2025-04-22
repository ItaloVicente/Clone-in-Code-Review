	@Test
	public void testCloneWithHeadSymRefIsMasterCopy() throws IOException
		git.checkout().setStartPoint("master").setCreateBranch(true).setName("master-copy").call();

		File directory = createTempDirectory("testCloneRepositorySymRef_master-copy");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals("refs/heads/master-copy"
	}

	@Test
	public void testCloneWithHeadSymRefIsNonMasterCopy() throws IOException
		git.checkout().setStartPoint("test").setCreateBranch(true).setName("test-copy").call();

		File directory = createTempDirectory("testCloneRepositorySymRef_test-copy");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals("refs/heads/test-copy"
	}

