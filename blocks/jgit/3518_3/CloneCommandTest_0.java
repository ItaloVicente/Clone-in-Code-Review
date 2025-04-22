	@Test
	public void testCloneRepositoryWhenDestinationDirectoryExistsAndIsNotEmpty() {
		try {
			String dirName = "testCloneTargetDirectoryNotEmpty";
			File directory = createTempDirectory(dirName);
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
				assertTrue(e.getMessage().contains("not an empty directory"));
				assertTrue(e.getMessage().contains(dirName));
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

