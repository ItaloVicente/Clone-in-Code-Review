    /**
     * Returns a new name for a copy of the resource at the given path in the given
     * workspace. This name could be determined either automatically or by querying
     * the user. This name will <b>not</b> be verified by the caller, so it must be
     * valid and unique.
     * <p>
     * Note this method is for internal use only.
     * </p>
     *
     * @param originalName the full path of the resource
     * @param workspace the workspace
     * @return the new full path for the copy, or <code>null</code> if the resource
     *   should not be copied
     */
    public static IPath getNewNameFor(IPath originalName, IWorkspace workspace) {
        return CopyFilesAndFoldersOperation.getAutoNewNameFor(originalName,
                workspace);
    }
