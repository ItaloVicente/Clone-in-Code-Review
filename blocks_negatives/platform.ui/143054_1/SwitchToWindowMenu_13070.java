    /**
     * Returns the text for a window. This may be truncated to fit
     * within the MAX_TEXT_LENGTH.
     */
    private String calcText(int number, IWorkbenchWindow window) {
        String suffix = window.getShell().getText();
        if (suffix == null) {
