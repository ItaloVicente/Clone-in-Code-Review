		boolean isShowingInternal = editorTableViewer.getInput() == getInternalEditors();
		if (showInternal != isShowingInternal) {
			newSelection = hiddenSelectedEditor;
			newTopIndex = hiddenTableTopIndex;
			if (editorTable.getSelectionIndex() != -1) {
				hiddenSelectedEditor = (EditorDescriptor) editorTable.getSelection()[0].getData();
				hiddenTableTopIndex = editorTable.getTopIndex();
