        }

        sourceNameField.setFocus();
        return null;
    }

    /**
     *	Answer a handle to the zip file currently specified as being the source.
     *	Return null if this file does not exist or is not of valid format.
     */
    protected TarFile getSpecifiedTarSourceFile() {
        return getSpecifiedTarSourceFile(sourceNameField.getText());
    }

    /**
     *	Answer a handle to the zip file currently specified as being the source.
     *	Return null if this file does not exist or is not of valid format.
     */
    private TarFile getSpecifiedTarSourceFile(String fileName) {
        if (fileName.length() == 0) {
