		assertNotNull(dialog);
        dialog.setBlockOnOpen(false);
        dialog.open();
        Shell shell = dialog.getShell();
		verifyCompositeText(shell);
		dialog.close();
		_verifyDialog.buttonPressed(IDialogConstants.YES_ID);
