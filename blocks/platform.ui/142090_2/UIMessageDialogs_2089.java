		DialogCheck.assertDialog(dialog);
	}

	public void testWizardOverwrite() {
		Dialog dialog = new MessageDialog(getShell(), "OK?", null, "Exists", MessageDialog.QUESTION, 0,
				IDialogConstants.YES_LABEL,
				IDialogConstants.YES_TO_ALL_LABEL,
				IDialogConstants.NO_LABEL,
				IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialog(dialog);
	}
