    /**
     * Open an error style dialog for PartInitException by
     * including any extra information from the nested
     * CoreException if present.
     */
    public static void openError(Shell parent, String title, String message,
            PartInitException exception) {
        CoreException nestedException = null;
        IStatus status = exception.getStatus();
        if (status != null && status.getException() instanceof CoreException) {
