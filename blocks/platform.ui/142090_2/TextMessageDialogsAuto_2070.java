	public void testCloseFileDeleted() {
		Dialog dialog = null;
		ResourceBundle bundle = ResourceBundle
				.getBundle("org.eclipse.ui.texteditor.EditorMessages");
		if (bundle != null) {
			dialog = getConfirmDialog(
					bundle
							.getString("Editor_error_activated_deleted_close_title"),
					bundle
							.getString("Editor_error_activated_deleted_close_message"));
		}
		DialogCheck.assertDialogTexts(dialog);
	}
