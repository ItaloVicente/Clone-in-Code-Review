
	@Test
	public void testCloneWithDepth() throws IOException
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"
		File directory = createTempDirectory("testCloneWithDepth");
		Git git = Git.cloneRepository()
					 .setDirectory(directory)
					 .setDepth(1)
					 .setURI(smartAuthNoneURI.toString())
					 .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
	}

	@Test
	public void testCloneWithDeepenSince() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"
		RevCommit commit = remoteRepository.commit()
										   .parent(remoteRepository.git().log().call().iterator().next())
										   .message("Test")
										   .add("test.txt"
										   .create();
		remoteRepository.update(master

		File directory = createTempDirectory("testCloneWithDeepenSince");
		Git git = Git.cloneRepository()
					 .setDirectory(directory)
					 .setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()))
					 .setURI(smartAuthNoneURI.toString())
					 .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
	}

	@Test
	public void testCloneWithDeepenNot() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"
		RevCommit commit = remoteRepository.git().log().call().iterator().next();
		remoteRepository.update(master
														.parent(commit)
														.message("Test")
														.add("test.txt"
														.create());

		File directory = createTempDirectory("testCloneWithDeepenNot");
		Git git = Git.cloneRepository()
					 .setDirectory(directory)
					 .addShallowExclude(commit.getId())
					 .setURI(smartAuthNoneURI.toString())
					 .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
	}

    @Test
    public void testV2CloneWithDepth() throws IOException
        File directory = createTempDirectory("testV2CloneWithDepth");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .setDepth(1)
                     .setURI(smartAuthNoneURI.toString())
                     .call();

        assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
    }

    @Test
    public void testV2CloneWithDeepenSince() throws Exception {
        RevCommit commit = remoteRepository.commit()
                                           .parent(remoteRepository.git().log().call().iterator().next())
                                           .message("Test")
                                           .add("test.txt"
                                           .create();
        remoteRepository.update(master

        File directory = createTempDirectory("testV2CloneWithDeepenSince");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()))
                     .setURI(smartAuthNoneURI.toString())
                     .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
    }

    @Test
    public void testV2CloneWithDeepenNot() throws Exception {
        RevCommit commit = remoteRepository.git().log().call().iterator().next();
        remoteRepository.update(master
                                                        .parent(commit)
                                                        .message("Test")
                                                        .add("test.txt"
                                                        .create());

        File directory = createTempDirectory("testV2CloneWithDeepenNot");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .addShallowExclude(commit.getId())
                     .setURI(smartAuthNoneURI.toString())
                     .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD))
    }
