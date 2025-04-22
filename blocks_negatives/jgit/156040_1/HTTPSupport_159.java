    protected JGitFileSystemProvider resolveProvider() {
        try {
            return null;
        } catch (Exception ex) {
            LOG.error("Error trying to resolve JGitFileSystemProvider.", ex);
        }
        return null;
    }
