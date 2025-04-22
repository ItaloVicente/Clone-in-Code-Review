            int overwrite = d.open();
            switch (overwrite) {
                break;
                return;
            default:
                cancelPressed();
                return;
            }
        }

        result = path;
        close();
    }

    /**
     * Sets the completion state of this dialog and adjusts the enable state of
     * the Ok button accordingly.
     *
     * @param value <code>true</code> if this dialog is compelete, and
     *  <code>false</code> otherwise
     */
    protected void setDialogComplete(boolean value) {
        okButton.setEnabled(value);
    }

    /**
     * Sets the original file to use.
     *
     * @param originalFile the original file
     */
    public void setOriginalFile(IFile originalFile) {
        this.originalFile = originalFile;
    }

    /**
     * Set the original file name to use.
     * Used instead of <code>setOriginalFile</code>
     * when the original resource is not an IFile.
     * Must be called before <code>create</code>.
     * @param originalName default file name
     */
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    /**
     * Returns whether this page's visual components all contain valid values.
     *
     * @return <code>true</code> if valid, and <code>false</code> otherwise
     */
    private boolean validatePage() {
        if (!resourceGroup.areAllValuesValid()) {
