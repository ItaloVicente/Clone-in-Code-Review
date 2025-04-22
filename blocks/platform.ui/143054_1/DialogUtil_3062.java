	private DialogUtil() {
	}

	public static void openError(Shell parent, String title, String message, PartInitException exception) {
		CoreException nestedException = null;
		IStatus status = exception.getStatus();
		if (status != null && status.getException() instanceof CoreException) {
