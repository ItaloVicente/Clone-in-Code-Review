        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Selection:
				EditorSelectionDialog dialog = new EditorSelectionDialog(menu.getShell());
				String fileName = fileResource.getName();
				dialog.setFileName(fileName);
				dialog.setMessage(NLS.bind(IDEWorkbenchMessages.OpenWithMenu_OtherDialogDescription, fileName));
				if (dialog.open() == Window.OK) {
					IEditorDescriptor editor = dialog.getSelectedEditor();
					if (editor != null) {
						openEditor(editor, editor.isOpenExternal());
