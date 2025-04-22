    /**
     * Creates a folder resource handle for the folder with the given name.
     * This method does not create the folder resource; this is the responsibility
     * of <code>createFolder</code>.
     *
     * @param container the resource container
     * @param folderName the name of the folder
     * @return the new folder resource handle
     */
    private IFolder createFolderHandle(IContainer container, String folderName) {
        return container.getFolder(new Path(folderName));
    }
