        }
    }

    /**
     * Queries the user whether the resource with the specified path should be
     * overwritten by a file system object that is being imported.
     *
     * @param resourcePath the workspace path of the resource that needs to be overwritten
     * @return <code>true</code> to overwrite, <code>false</code> to not overwrite
     * @exception OperationCanceledException if canceled
     */
    boolean queryOverwrite(IPath resourcePath)
            throws OperationCanceledException {
        String overwriteAnswer = overwriteCallback.queryOverwrite(resourcePath
                .makeRelative().toString());

        if (overwriteAnswer.equals(IOverwriteQuery.CANCEL)) {
