	public void testUpdateConflict() {
		MessageDialog dialog = null;
		ResourceBundle bundle = ResourceBundle
				.getBundle("org.eclipse.ui.texteditor.EditorMessages");
		if (bundle != null) {
			dialog = getQuestionDialog(bundle
					.getString("Editor_error_save_outofsync_title"), bundle
					.getString("Editor_error_save_outofsync_message"));
		}
		DialogCheck.assertDialogTexts(dialog);
	}
