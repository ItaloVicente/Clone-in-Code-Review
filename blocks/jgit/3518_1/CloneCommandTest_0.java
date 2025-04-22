	@Test
	public void testCloneRepositoryWhenDestinationDirectoryExistsAndIsNotEmpty() {
		try {
			File directory = createTempDirectory("testCloneRepository");
			CloneCommand command = Git.cloneRepository();
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			Git git2 = command.call();
			assertNotNull(git2);
			command = Git.cloneRepository();
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			try {
				git2 = command.call();
				fail("destination directory already exists and is not an empty folder
			} catch (JGitInternalException e) {
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

