    @Test
	public void testV2CloneWithDepth() throws Exception {
        File directory = createTempDirectory("testV2CloneWithDepth");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .setDepth(1)
                     .setURI(smartAuthNoneURI.toString())
                     .call();

        assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD)), git.getRepository().getObjectDatabase().getShallowCommits());
    }

    @Test
    public void testV2CloneWithDeepenSince() throws Exception {
        RevCommit commit = remoteRepository.commit()
                                           .parent(remoteRepository.git().log().call().iterator().next())
                                           .message("Test")
                                           .add("test.txt", "Hello world")
                                           .create();
        remoteRepository.update(master, commit);

        File directory = createTempDirectory("testV2CloneWithDeepenSince");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()))
                     .setURI(smartAuthNoneURI.toString())
                     .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD)), git.getRepository().getObjectDatabase().getShallowCommits());
    }

    @Test
    public void testV2CloneWithDeepenNot() throws Exception {
        RevCommit commit = remoteRepository.git().log().call().iterator().next();
        remoteRepository.update(master, remoteRepository.commit()
                                                        .parent(commit)
                                                        .message("Test")
                                                        .add("test.txt", "Hello world")
                                                        .create());

        File directory = createTempDirectory("testV2CloneWithDeepenNot");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .addShallowExclude(commit.getId())
                     .setURI(smartAuthNoneURI.toString())
                     .call();

		assertEquals(Set.of(git.getRepository().resolve(Constants.HEAD)), git.getRepository().getObjectDatabase().getShallowCommits());
    }
