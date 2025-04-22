        if (rootEntry != null) {
            rootEntry.setValues(input);
            updateChildrenOf(rootEntry, tree);
        }

    	updateStatusLine(null);
    }

    /**
     * Sets the message to be displayed in the status line. This message is
     * displayed when there is no error message.
     *
     * @param message
     *            the message to be displayed, or <code>null</code>
     */
    private void setMessage(String message) {
        if (statusLineManager != null) {
