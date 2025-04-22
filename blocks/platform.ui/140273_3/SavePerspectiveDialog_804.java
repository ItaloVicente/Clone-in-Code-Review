		perspName = text.getText();
		persp = perspReg.findPerspectiveWithLabel(perspName);
		if (persp != null) {
			String message = NLS.bind(WorkbenchMessages.SavePerspective_overwriteQuestion, perspName);
			String[] buttons = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
					IDialogConstants.CANCEL_LABEL };
			MessageDialog d = new MessageDialog(this.getShell(), WorkbenchMessages.SavePerspective_overwriteTitle, null,
