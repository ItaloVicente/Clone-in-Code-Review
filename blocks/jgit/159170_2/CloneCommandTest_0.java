	@Test
	public void testCloneNoTags() throws IOException
			GitAPIException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setNoTags();
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNotNull(git2.getRepository().resolve("refs/heads/test"));
		assertNull(git2.getRepository().resolve("tag-initial"));
		assertNull(git2.getRepository().resolve("tag-for-blob"));
		RemoteConfig remoteConfig = new RemoteConfig(
				git2.getRepository().getConfig()
		assertEquals(TagOpt.NO_TAGS
	}

	@Test
	public void testCloneFollowTags() throws IOException
			GitAPIException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(
				Collections.singletonList("refs/heads/master"));
		command.setTagOption(TagOpt.FETCH_TAGS);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertNull(git2.getRepository().resolve("refs/heads/test"));
		assertNotNull(git2.getRepository().resolve("tag-initial"));
		assertNotNull(git2.getRepository().resolve("tag-for-blob"));
		RemoteConfig remoteConfig = new RemoteConfig(
				git2.getRepository().getConfig()
		assertEquals(TagOpt.FETCH_TAGS
	}

