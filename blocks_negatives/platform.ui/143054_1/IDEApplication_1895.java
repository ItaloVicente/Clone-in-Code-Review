    /**
     * Look at the argument URL for the workspace's version information. Return
     * that version if found and null otherwise.
     */
    private static Version readWorkspaceVersion(URL workspace) {
        File versionFile = getVersionFile(workspace, false);
        if (versionFile == null || !versionFile.exists()) {
