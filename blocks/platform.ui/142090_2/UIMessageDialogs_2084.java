		Dialog dialog = new MessageDialog(getShell(), title, null, // accept the default window icon
				msg, MessageDialog.QUESTION, 0,
				IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialog(dialog);
	}

	public void testDeleteReadOnlyCheck() {
		Dialog dialog = new MessageDialog(getShell(),"Delete?", null,
				"This?",
				MessageDialog.QUESTION, 0,
						IDialogConstants.YES_LABEL,
						IDialogConstants.YES_TO_ALL_LABEL,
						IDialogConstants.NO_LABEL,
						IDialogConstants.CANCEL_LABEL);
		DialogCheck.assertDialog(dialog);
	}

	public void testDeleteResource() {
		Dialog dialog = getQuestionDialog("Delete","Delete?");
		DialogCheck.assertDialog(dialog);
	}

	public void testDeleteResources() {
		Dialog dialog = getQuestionDialog("Delete","OK?");
		DialogCheck.assertDialog(dialog);
	}

	public void testDropOverwrite() {
		Dialog dialog = new MessageDialog(
				getShell(),
				ResourceNavigatorMessages.DropAdapter_question,
