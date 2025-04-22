		if (descriptor != null) {
			descriptor = IDE.overrideDefaultEditorAssociation(editorInput, type,
					descriptor);
		}
		if (descriptor == null) {
			descriptor = registry.findEditor(EditorsUI.DEFAULT_TEXT_EDITOR_ID);
