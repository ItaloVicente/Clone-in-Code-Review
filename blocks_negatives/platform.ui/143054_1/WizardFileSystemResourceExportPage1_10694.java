    /**
     * Attempts to ensure that the specified directory exists on the local file system.
     * Answers a boolean indicating success.
     *
     * @return boolean
     * @param directory java.io.File
     */
    protected boolean ensureDirectoryExists(File directory) {
        if (!directory.exists()) {
            if (!queryYesNoQuestion(DataTransferMessages.DataTransfer_createTargetDirectory)) {
