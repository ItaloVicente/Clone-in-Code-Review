	@Test
	public void testCloneRepositoryDefaultDirectory() throws IOException
			JGitInternalException
		CloneCommand command = Git.cloneRepository().setURI(fileUri());

		command.verifyDirectories(new URIish(fileUri()));
		File directory = command.getDirectory();
		assertEquals(git.getRepository().getWorkTree().getName()
	}

	@Test
	public void testCloneBareRepositoryDefaultDirectory() throws IOException
			JGitInternalException
		CloneCommand command = Git.cloneRepository().setURI(fileUri()).setBare(true);

		command.verifyDirectories(new URIish(fileUri()));
		File directory = command.getDirectory();
		assertEquals(git.getRepository().getWorkTree().getName() + Constants.DOT_GIT_EXT
	}

