    /**
     * Returns the operation to perform when this action runs.
     *
     * @return the operation to perform when this action runs.
     */
    protected CopyFilesAndFoldersOperation createOperation() {
        return new CopyFilesAndFoldersOperation(getShell());
    }
