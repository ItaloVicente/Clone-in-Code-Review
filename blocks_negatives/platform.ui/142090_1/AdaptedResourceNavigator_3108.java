		        .getText(element);
    }

    /**
     * Handles double clicks in viewer.
     * Opens editor if file double-clicked.
     * @since 2.0
     */
    protected void handleDoubleClick(DoubleClickEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event
