    @Test
    public void testCloneRepositoryWithDepth() throws IOException
		File directory = createTempDirectory("testCloneRepositoryWithDepth");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
        command.setDepth(1);
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertEquals(Set.of(git2.getRepository().resolve(Constants.HEAD))
	}

    @Test
	public void testCloneRepositoryWithShallowSince() throws Exception {
		RevCommit commit = tr.commit()
							 .parent(tr.git().log().call().iterator().next())
							 .message("Test")
							 .add("test.txt"
							 .create();
		tr.update("refs/heads/test"

        File directory = createTempDirectory("testCloneRepositoryWithShallowSince");
        CloneCommand command = Git.cloneRepository();
        command.setDirectory(directory);
        command.setURI(fileUri());
        command.setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()));
        command.setBranchesToClone(Set.of("refs/heads/test"));
        Git git2 = command.call();
        addRepoToClose(git2.getRepository());

        assertEquals(Set.of(git2.getRepository().resolve(Constants.HEAD))
    }

	@Test
	public void testCloneRepositoryWithShallowExclude() throws Exception {
		RevCommit commit = tr.git().log().call().iterator().next();
		tr.update("refs/heads/test"
									   .parent(commit)
									   .message("Test")
									   .add("test.txt"
									   .create());

		File directory = createTempDirectory("testCloneRepositoryWithShallowExclude");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.addShallowExclude(commit.getId());
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertEquals(Set.of(git2.getRepository().resolve(Constants.HEAD))
	}

