
	@Override
	protected void createFieldEditors() {
		DirectoryFieldEditor editor = new DirectoryFieldEditor(
				UIPreferences.DEFAULT_REPO_DIR,
				UIText.GitPreferenceRoot_DefaultRepoFolderLabel,
				getFieldEditorParent()) {

			@Override
			protected void createControl(Composite parent) {
				super
						.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
				super.createControl(parent);
			}

		};
		editor.setEmptyStringAllowed(false);
		addField(editor);
		editor.getLabelControl(getFieldEditorParent()).setToolTipText(
				UIText.GitPreferenceRoot_DefaultRepoFolderTooltip);
	}
