    /**
     * Returns the current frame.
     *
     * @param flags a bit-wise OR of the frame source flag constants
     * @return the current frame
     */
    protected Frame getCurrentFrame(int flags) {
        Object input = viewer.getInput();
        TreeFrame frame = createFrame(input);
        if ((flags & IFrameSource.FULL_CONTEXT) != 0) {
            frame.setSelection(viewer.getSelection());
            frame.setExpandedElements(viewer.getExpandedElements());
        }
        return frame;
    }
