		File directory = createTempDirectory("testCloneRepositoryNoCheckout");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setNoCheckout(true);
		try (Git git2 = command.call()) {
			Repository clonedRepo = git2.getRepository();
			Ref main = clonedRepo.exactRef(Constants.R_HEADS + "test");
			assertNotNull(main);
			ObjectId id = main.getObjectId();
			assertNotNull(id);
			assertNotEquals(id
			ObjectId headId = clonedRepo.resolve(Constants.HEAD);
			assertEquals(id
			assertArrayEquals(new String[] { Constants.DOT_GIT }
					directory.list());
		}
	}

	@Test
	public void testCloneRepositoryRefLogForLocalRefs()
			throws IOException
		File directory = createTempDirectory(
				"testCloneRepositoryRefLogForLocalRefs");
