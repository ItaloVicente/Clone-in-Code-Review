    private JGitFileSystemLock createLock(long lastAccessThreshold) {
        Git gitMock = mock(Git.class);
        Repository repo = mock(Repository.class);
        File directory = mock(File.class);
        when(directory.isDirectory()).thenReturn(true);
        when(directory.toURI()).thenReturn(URI.create(""));
        when(repo.getDirectory()).thenReturn(directory);
        when(gitMock.getRepository()).thenReturn(repo);
        return new JGitFileSystemLock(gitMock,
                                      TimeUnit.MILLISECONDS,
                                      lastAccessThreshold) {
