		DialogCheck.assertDialogTexts(dialog);
	}

	public void testDeleteResource() {
		Dialog dialog = getQuestionDialog("Delete","");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testDeleteResources() {
		Dialog dialog = getQuestionDialog("Delete","");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testDropOverwrite() {
		Dialog dialog = new MessageDialog(
				getShell(),
				ResourceNavigatorMessages.DropAdapter_question,
