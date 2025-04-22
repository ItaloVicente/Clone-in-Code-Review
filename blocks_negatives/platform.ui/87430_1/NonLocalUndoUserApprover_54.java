		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog dialog = new MessageDialog(part.getSite().getShell(), title,
						null, message, MessageDialog.QUESTION, 0, IDialogConstants.OK_LABEL, discardButton,
		        answer[0] = dialog.open();
		}});
