    }

    /**
     * Displays an error message when a CoreException occured.
     *
     * @param exception the CoreException
     * @param shell parent shell for the message box
     * @param title the mesage box title
     * @param message additional error message
     */
    private void handleCoreException(CoreException exception, Shell shell,
            String title, String message) {
        IStatus status = exception.getStatus();
        if (status != null) {
            ErrorDialog.openError(shell, title, message, status);
        } else {
            MessageDialog
                    .openError(
                            shell,
                            IDEWorkbenchMessages.InternalError, exception.getLocalizedMessage());
        }
    }

    /**
     * Sets the checked state of tree items based on the initial
     * working set, if any.
     */
    private void initializeCheckedState() {
        BusyIndicator.showWhile(getShell().getDisplay(), () -> {
