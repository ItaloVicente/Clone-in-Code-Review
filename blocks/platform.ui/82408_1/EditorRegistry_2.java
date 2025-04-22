	private static boolean isSystem(String id) {
		return IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID.equals(id)
				|| IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID.equals(id);
	}

	private void lookupEditorFromTable(Map<String, IEditorDescriptor> editorTable, EditorDescriptor editor) {
		IEditorDescriptor validEditorDescritor = mapIDtoEditor.get(editor.getId());
		if (validEditorDescritor != null) {
			editorTable.put(validEditorDescritor.getId(), validEditorDescritor);
		}
	}

