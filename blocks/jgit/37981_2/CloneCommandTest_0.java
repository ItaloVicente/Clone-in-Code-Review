	@Test
	public void testCloneRepositoryExplicitGitDir() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setGitDir(new File(directory
		command.setURI(fileUri());
		Git git2 = command.call();
		assertEquals(directory
		assertEquals(new File(directory
				.getDirectory());
	}

	@Test
	public void testCloneRepositoryExplicitGitDirNonStd() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository");
		File gDir = createTempDirectory("testCloneRepository.git");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setGitDir(gDir);
		command.setURI(fileUri());
		Git git2 = command.call();
		assertEquals(directory
		assertEquals(gDir
				.getDirectory());
		assertTrue(new File(directory
		assertFalse(new File(gDir
	}

	@Test
	public void testCloneRepositoryExplicitGitDirBare() throws IOException
			JGitInternalException
		File gDir = createTempDirectory("testCloneRepository.git");
		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setGitDir(gDir);
		command.setURI(fileUri());
		Git git2 = command.call();
		try {
			assertNull(null
			fail("Expected NoWorkTreeException");
		} catch (NoWorkTreeException e) {
			assertEquals(gDir
		}
	}

