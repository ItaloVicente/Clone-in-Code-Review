    /*
     * Default Git preferences suitable for most of the tests. If specific test needs some custom configuration, it needs to
     * override this method and provide own map of preferences.
     */
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();
        gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED, "false");
        return gitPrefs;
    }
