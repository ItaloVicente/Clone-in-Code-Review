    /**
     * Answer the files property of this element. Answer an empty list if the
     * files property is null. This method should not be used to add children to
     * the receiver. Use addChild(FileSystemElement) instead.
     *
     * @return AdaptableList The list of files parented by the receiver.
     */
    public AdaptableList getFiles() {
        if (files == null) {
            files = new AdaptableList(0);
        }
        return files;
    }
