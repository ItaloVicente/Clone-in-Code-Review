    @Test
    public void testExecutedPostCommitHook() throws IOException {
        testHook("hook-repo-name-executed",
                 "post-commit",
                 true);
    }
