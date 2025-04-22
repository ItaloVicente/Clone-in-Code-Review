    /**
     * Updates the viewer in response to the current frame changing.
     *
     * @param frame the new value for the current frame
     */
    protected void frameChanged(TreeFrame frame) {
        viewer.getControl().setRedraw(false);
        viewer.setInput(frame.getInput());
        viewer.setExpandedElements(frame.getExpandedElements());
        viewer.setSelection(frame.getSelection(), true);
        viewer.getControl().setRedraw(true);
    }
