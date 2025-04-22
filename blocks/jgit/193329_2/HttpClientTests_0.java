
    @Test
    public void testCloneWithDepth() throws IOException
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
        RevCommit commit = remoteRepository.commit()
                                           .parent(remoteRepository.git().log().call().iterator().next())
                                           .message("Test")
                                           .add("test.txt"
                                           .create();
        remoteRepository.update(master

        File directory = createTempDirectory("testCloneWithDeepenSince");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .setDeepenSince(Instant.ofEpochSecond(commit.getCommitTime()))
                     .setURI(smartAuthNoneURI.toString())
                     .call();

        assertFalse(git.getRepository().getObjectDatabase().getShallowCommits().isEmpty());
    }

    @Test
    public void testCloneWithDeepenNot() throws Exception {
        RevCommit commit = remoteRepository.git().log().call().iterator().next();
        String deepenNotRef = "test";
        remoteRepository.branch(deepenNotRef).update(commit);
        remoteRepository.update(master
                                                        .parent(commit)
                                                        .message("Test")
                                                        .add("test.txt"
                                                        .create());

        File directory = createTempDirectory("testCloneWithDeepenSince");
        Git git = Git.cloneRepository()
                     .setDirectory(directory)
                     .addDeepenNotRef("test")
                     .setURI(smartAuthNoneURI.toString())
                     .call();

        assertFalse(git.getRepository().getObjectDatabase().getShallowCommits().isEmpty());
    }
