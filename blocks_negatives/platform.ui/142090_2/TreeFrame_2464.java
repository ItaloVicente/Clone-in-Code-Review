    /**
     * Restore the frame from the specified memento.
     *
     * @param memento memento to restore frame from
     */
    public void restoreState(IMemento memento) {
        IMemento childMem = memento.getChild(TAG_FRAME_INPUT);
