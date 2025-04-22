        return DataTransferMessages.ArchiveImport_fromFile;
    }

    /**
     *	Answer a handle to the zip file currently specified as being the source.
     *	Return null if this file does not exist or is not of valid format.
     */
    protected ZipFile getSpecifiedZipSourceFile() {
        return getSpecifiedZipSourceFile(sourceNameField.getText());
    }

    /**
     *	Answer a handle to the zip file currently specified as being the source.
     *	Return null if this file does not exist or is not of valid format.
     */
    private ZipFile getSpecifiedZipSourceFile(String fileName) {
        if (fileName.length() == 0) {
