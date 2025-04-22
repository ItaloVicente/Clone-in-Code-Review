        return text;
    }

    /**
     * Returns the tool tip text for the given element.
     * Used as the tool tip text for the current frame, and for the view title tooltip.
     */
    String getFrameToolTipText(Object element) {
        if (element instanceof IResource) {
            IPath path = ((IResource) element).getFullPath();
            if (path.isRoot()) {
