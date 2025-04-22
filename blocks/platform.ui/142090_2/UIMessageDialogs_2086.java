		DialogCheck.assertDialogTexts(dialog);
		DialogCheck.assertDialog(dialog);
	}

	public void testRenameOverwrite() {
		Dialog dialog = getQuestionDialog("Exists","Overwrite");
