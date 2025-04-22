    private static final String
            TARGET_GIT = "target/target",
            SOURCE_GIT = "source/source";

    @Test
    public void testToCloneSuccess() throws IOException, GitAPIException {
        final File parentFolder = createTempDirectory();
