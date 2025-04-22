    @Override
    public Map<String, String> getGitPreferences() {
        Map<String, String> gitPrefs = super.getGitPreferences();
        try {
            final File hooksDir = createTempDirectory();
            gitPrefs.put(HOOK_DIR,
                         hooksDir.getAbsolutePath());
