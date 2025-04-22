	public String modifyCommitMessage(final String commitMessage) {
		final String[] result = new String[1];
		result[0] = commitMessage;
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			public void run() {
				InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(),
						UIText.RebaseInteractiveHandler_EditMessageDialogTitle,
						UIText.RebaseInteractiveHandler_EditMessageDialogText,
						commitMessage, null);
				if (dialog.open() == Window.OK) {
					result[0] = dialog.getValue();
				}
			}
		});
		return result[0];
