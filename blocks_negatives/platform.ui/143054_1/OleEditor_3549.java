        return true;
    }

    /**
     *Since we don't know when a change has been made, always answer true
     * @return <code>false</code> if it was not opened and <code>true</code>
     * only if it is dirty
     */
    public boolean isSaveNeeded() {
        return isDirty();
    }

    /**
     * Save the supplied file using the SWT API.
     * @param file java.io.File
     * @return <code>true</code> if the save was successful
     */
    private boolean saveFile(File file) {

        file.renameTo(tempFile);
        boolean saved = false;
        if (OLE.isOleFile(file) || usesStorageFiles(clientSite.getProgramID())) {
            saved = clientSite.save(file, true);
        } else {
            saved = clientSite.save(file, false);
        }

        if (saved) {
            tempFile.delete();
            return true;
        }
        tempFile.renameTo(file);
        return false;
    }

    /**
     * Save the new File using the client site.
     * @return WorkspaceModifyOperation
     */
    private WorkspaceModifyOperation saveNewFileOperation() {

        return new WorkspaceModifyOperation() {
            @Override
