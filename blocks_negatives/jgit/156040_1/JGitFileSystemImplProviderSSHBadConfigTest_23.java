    @Override
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();

        gitPrefs.put(GIT_SSH_ENABLED,
                     "true");
        gitPrefs.put(GIT_SSH_IDLE_TIMEOUT,
                     "bz");

        return gitPrefs;
    }

    @Test
    public void testCheckDefaultSSHIdleWithInvalidArg() throws IOException {
        assertEquals(JGitFileSystemProviderConfiguration.DEFAULT_SSH_IDLE_TIMEOUT,
                     provider.getGitSSHService().getProperties().get(SshServer.IDLE_TIMEOUT));
    }
