		TableItem[] items = resourceTypeTable.getItems();
		FileEditorMapping[] resourceTypes = new FileEditorMapping[items.length];
		for (int i = 0; i < items.length; i++) {
			resourceTypes[i] = (FileEditorMapping) (items[i].getData());
		}
		EditorRegistry registry = (EditorRegistry) WorkbenchPlugin.getDefault().getEditorRegistry(); // cast to allow
		registry.setFileEditorMappings(resourceTypes);
		registry.saveAssociations();

		PrefUtil.savePrefs();
		return true;
	}

	public void promptForEditor() {
		EditorSelectionDialog dialog = new EditorSelectionDialog(getControl().getShell());
		dialog.setEditorsToFilter(getAssociatedEditors());
		dialog.setMessage(NLS.bind(WorkbenchMessages.Choose_the_editor_for_file, getSelectedResourceType().getLabel()));
		if (dialog.open() == Window.OK) {
			EditorDescriptor editor = (EditorDescriptor) dialog.getSelectedEditor();
			if (editor != null) {
				int i = editorTable.getItemCount();
				boolean isEmpty = i < 1;
				TableItem item = new TableItem(editorTable, SWT.NULL, i);
				item.setData(DATA_EDITOR, editor);
				if (isEmpty) {
					item.setText(editor.getLabel() + " " + WorkbenchMessages.FileEditorPreference_defaultLabel); //$NON-NLS-1$
