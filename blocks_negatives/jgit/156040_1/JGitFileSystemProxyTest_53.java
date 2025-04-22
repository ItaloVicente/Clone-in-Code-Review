    private int gitDaemonPort;

    @Override
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();
        gitPrefs.put(GIT_DAEMON_ENABLED,
                     "true");
        gitDaemonPort = findFreePort();
        gitPrefs.put(GIT_DAEMON_PORT,
                     String.valueOf(gitDaemonPort));
        return gitPrefs;
    }

    @Test
    public void proxyTest() throws IOException {

        final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo,
                                                                              Collections.emptyMap());

        assertTrue(origin instanceof JGitFileSystemProxy);
        JGitFileSystemProxy proxy = (JGitFileSystemProxy) origin;
        JGitFileSystem realJGitFileSystem = proxy.getRealJGitFileSystem();
        assertTrue(realJGitFileSystem instanceof JGitFileSystemImpl);

        assertTrue(proxy.equals(realJGitFileSystem));
        assertTrue(realJGitFileSystem.equals(proxy));
    }
