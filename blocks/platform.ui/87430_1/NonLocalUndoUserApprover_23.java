		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			MessageDialog dialog = new MessageDialog(part.getSite().getShell(), title,
					null, message, MessageDialog.QUESTION, 0, IDialogConstants.OK_LABEL, discardButton,
					IDialogConstants.CANCEL_LABEL); // yes is the default
		    answer[0] = dialog.open();
});
