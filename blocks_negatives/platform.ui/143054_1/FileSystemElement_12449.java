    /**
     * Adds the passed child to this object's collection of children.
     *
     * @param child
     *            FileSystemElement
     */
    public void addChild(FileSystemElement child) {
        if (child.isDirectory()) {
            if (folders == null) {
