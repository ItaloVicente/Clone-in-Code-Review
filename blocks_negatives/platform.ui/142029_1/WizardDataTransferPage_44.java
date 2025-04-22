        return complete;
    }

    /**
     * Get a path from the supplied text widget.
     * @return org.eclipse.core.runtime.IPath
     */
    protected IPath getPathFromText(Text textField) {
        String text = textField.getText();
        if (text.length() == 0) {
