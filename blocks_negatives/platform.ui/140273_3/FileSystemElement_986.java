    /**
     * Returns a list of the folders that are immediate children of this folder.
     * Answer an empty list if the folders property is null. This method should
     * not be used to add children to the receiver. Use
     * addChild(FileSystemElement) instead.
     *
     * @return AdapatableList The list of folders parented by the receiver.
     */
    public AdaptableList getFolders() {
        if (folders == null) {
            folders = new AdaptableList(0);
        }
        return folders;
    }
