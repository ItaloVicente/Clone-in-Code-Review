		return result[0];
	}

	private boolean getSingleSpecial(Shell shell, URIish uri, CredentialItem item) {
		if (item instanceof CredentialItem.InformationalMessage) {
			MessageDialog.openInformation(shell, UIText.EGitCredentialsProvider_information, item.getPromptText());
			return true;
		} else if (item instanceof CredentialItem.YesNoType) {
			CredentialItem.YesNoType v = (CredentialItem.YesNoType) item;
			String[] labels = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
			int[] resultIDs = new int[] { IDialogConstants.YES_ID, IDialogConstants.NO_ID, IDialogConstants.CANCEL_ID };
			
			MessageDialog dialog = new MessageDialog(
					shell, 
					UIText.EGitCredentialsProvider_question,
					null,
					item.getPromptText(), 
					MessageDialog.QUESTION_WITH_CANCEL,
					labels,
					0);
			dialog.setBlockOnOpen(true);
			int r = dialog.open();
			if (r < 0) {
				return false;
			}
			
			switch (resultIDs[r]) {
			case IDialogConstants.YES_ID: {
				v.setValue(true);
				return true;
			}
			case IDialogConstants.NO_ID: {
				v.setValue(false);
				return true;
			}
			default:
