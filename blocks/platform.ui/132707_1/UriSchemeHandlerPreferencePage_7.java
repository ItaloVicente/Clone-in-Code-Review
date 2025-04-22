	interface IStatusManagerWrapper {
		default void handle(IStatus status, int style) {
			StatusManager.getManager().handle(status, style);
		}
	}

	interface IMessageDialogWrapper {
		default void openWarning(Shell shell, String title, String message) {
			MessageDialog.openWarning(shell, title, message);
		}
