		Map<String, IEditorDescriptor> map = new HashMap<>(initialSize);
		addSystemEditors(map);
		return map;
	}

	private void addSystemEditors(Map<String, IEditorDescriptor> map) {
		EditorDescriptor editor = new EditorDescriptor();
		editor.setID(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
		editor.setName(WorkbenchMessages.SystemEditorDescription_name);
		editor.setOpenMode(EditorDescriptor.OPEN_EXTERNAL);
		map.put(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID, editor);

		if (ComponentSupport.inPlaceEditorSupported()) {
			editor = new EditorDescriptor();
			editor.setID(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
			editor.setName(WorkbenchMessages.SystemInPlaceDescription_name);
			editor.setOpenMode(EditorDescriptor.OPEN_INPLACE);
			map.put(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID, editor);
		}
