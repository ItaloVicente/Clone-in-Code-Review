		Listener treeListener = event -> {
			if (!editorTableViewer.getSelection().isEmpty()) {
				selectedEditor = (EditorDescriptor) editorTableViewer.getStructuredSelection().getFirstElement();
			} else {
				selectedEditor = null;
				okButton.setEnabled(false);
			}
			updateEnableState();
		};
		tree.addListener(SWT.Selection, treeListener);
		tree.addListener(SWT.DefaultSelection, treeListener);
		tree.addListener(SWT.MouseDoubleClick, event -> handleDoubleClickEvent());
