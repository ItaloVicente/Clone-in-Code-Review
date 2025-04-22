		int severity;
		String title;
		String message;
		if (versionCompareResult < 0) {
			severity = MessageDialog.INFORMATION;
			title = IDEWorkbenchMessages.IDEApplication_versionTitle_olderWorkspace;
			message = NLS.bind(IDEWorkbenchMessages.IDEApplication_versionMessage_olderWorkspace, url.getFile());
		} else {
			severity = MessageDialog.WARNING;
			title = IDEWorkbenchMessages.IDEApplication_versionTitle_newerWorkspace;
			message = NLS.bind(IDEWorkbenchMessages.IDEApplication_versionMessage_newerWorkspace, url.getFile());
		}

		MessageDialog dialog = new MessageDialog(shell, title, null, message, severity,
				new String[] {IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL}, 0);
		return dialog.open() == Window.OK;
