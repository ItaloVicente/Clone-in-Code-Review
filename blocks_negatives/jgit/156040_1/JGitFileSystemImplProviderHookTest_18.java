    @Test
    public void testNotSupportedPreCommitHook() throws IOException {
        testHook("hook-repo-name-executed-pre-commit",
                 "pre-commit",
                 false);
    }
