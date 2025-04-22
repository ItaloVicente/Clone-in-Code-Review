    @Override
    public Map<String, String> getGitPreferences() {
        try {
            tempDir = createTempDirectory();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        Map<String, String> gitPrefs = super.getGitPreferences();
        gitPrefs.put(GIT_NIO_DIR,
                     tempDir.toString());
        if (!REPOSITORIES_CONTAINER_DIR.equals(dirPathName)) {
            gitPrefs.put(GIT_NIO_DIR_NAME,
                         dirPathName);
        }
        return gitPrefs;
    }
