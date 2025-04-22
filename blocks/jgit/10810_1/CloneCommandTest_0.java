				fetchRefSpec(git2.getRepository()));
	}

	@Test
	public void testBareCloneRepository() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testCloneRepository_bare");
		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
				fetchRefSpec(git2.getRepository()));
	}

	public static RefSpec fetchRefSpec(Repository r) throws URISyntaxException {
		RemoteConfig remoteConfig =
				new RemoteConfig(r.getConfig()
		return remoteConfig.getFetchRefSpecs().get(0);
