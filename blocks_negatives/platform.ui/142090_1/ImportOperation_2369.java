        return true;
    }

    /**
     * Returns whether the given file should be overwritten.
     *
     * @param targetFile the file to ask to overwrite
     * @param policy determines if the user is queried for overwrite
     * @return <code>true</code> if the file should be overwritten, and
     * 	<code>false</code> if not.
     */
    boolean queryOverwriteFile(IFile targetFile, int policy) {
        if (policy != POLICY_FORCE_OVERWRITE) {
            if (this.overwriteState == OVERWRITE_NOT_SET
                    && !queryOverwrite(targetFile.getFullPath())) {
