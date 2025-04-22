
    @Test(expected = JGitInternalException.class)
    public void shouldNotBePossibleToCreateEmptyCommitByDefault() throws Exception {
        Git git = new Git(db);
        git.commit().setMessage("This is an empty commit!").call();
    }

    @Test(expected = JGitInternalException.class)
    public void shouldNotBePossibleToCreateEmptyCommitIfAllowEmptyIsSetToFalse() throws Exception {
        Git git = new Git(db);
        git.commit().setMessage("This is an empty commit!").setAllowEmpty(false).call();
    }

    @Test
    public void shouldBePossibleToCreateEmptyCommitIfAllowEmptyIsSetToTrue() throws Exception {
        final String COMMIT_MESSAGE = "This is an empty commit!";
        Git git = new Git(db);
        assertTrue(git.status().call().isClean());
        RevCommit emptyCommit = git.commit().setMessage(COMMIT_MESSAGE).setAllowEmpty(true).call();

        assertEquals(COMMIT_MESSAGE
    }
