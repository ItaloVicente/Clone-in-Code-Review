	@Test
	public void testCloneRepositoryDefaultDirectory() throws IOException
			JGitInternalException
		CloneCommand command = Git.cloneRepository();
		command.setURI(fileUri());

		command.verifyDirectories(new URIish(fileUri()));
		File directory = command.getDirectory();
		assertEquals(git.getRepository().getWorkTree().getName()
	}

	@Test
	public void testCloneBareRepositoryDefaultDirectory() throws IOException
			JGitInternalException
		CloneCommand command = Git.cloneRepository();
		command.setURI(fileUri());
		command.setBare(true);

		command.verifyDirectories(new URIish(fileUri()));
		File directory = command.getDirectory();
		assertEquals(git.getRepository().getWorkTree().getName() + ".git"
	}

