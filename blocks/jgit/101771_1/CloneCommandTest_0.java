	@Test
	public void testCloneRepositoryDefaultDirectory() throws IOException
			JGitInternalException
		CloneCommand command = Git.cloneRepository();
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(git.getRepository().getWorkTree().getName()
				git2.getRepository().getWorkTree().getName());
	}

