		Object[] input = (Object[]) editorTableViewer.getInput();
		if (input != null) {
			boolean isShowingInternal = Arrays.equals(input, getInternalEditors());
			if (showInternal != isShowingInternal) {
				newSelection = hiddenSelectedEditor;
				if (!editorTableViewer.getSelection().isEmpty()) {
					hiddenSelectedEditor = (EditorDescriptor) editorTableViewer.getStructuredSelection()
							.getFirstElement();
				}
