	}

	public void testCloseFileDeleted() {
		Dialog dialog = getConfirmDialog(
				getEditorString("Editor_error_activated_deleted_close_title"),
				getEditorString("Editor_error_activated_deleted_close_message"));
		DialogCheck.assertDialog(dialog);
	}

	public void testFileChanged() {
		MessageDialog dialog = getQuestionDialog(
				getEditorString("Editor_error_activated_outofsync_title"),
				getEditorString("Editor_error_activated_outofsync_message"));
		DialogCheck.assertDialog(dialog);
	}


	public void testSaveFileDeleted() {
		MessageDialog dialog = new MessageDialog(
				getShell(),
				getEditorString("Editor_error_activated_deleted_save_title"),
				null,
				getEditorString("Editor_error_activated_deleted_save_message"),
				MessageDialog.QUESTION, 0,
