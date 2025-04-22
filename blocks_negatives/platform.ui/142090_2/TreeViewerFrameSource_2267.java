    /**
     * Returns the parent frame, or <code>null</code> if there is no parent frame.
     *
     * @param flags a bit-wise OR of the frame source flag constants
     * @return the parent frame, or <code>null</code>
     */
    protected Frame getParentFrame(int flags) {
        Object input = viewer.getInput();
        ITreeContentProvider provider = (ITreeContentProvider) viewer
                .getContentProvider();
        Object parent = provider.getParent(input);
        if (parent == null) {
            return null;
        }
