		MessageDialog dialog = new MessageDialog(
				page.getWorkbenchWindow().getShell(),
				WorkbenchMessages.EditorManager_reuseEditorDialogTitle,
				null, // accept the default window icon
				NLS.bind(WorkbenchMessages.EditorManager_saveChangesQuestion, dirtyEditor.getName()),
				MessageDialog.QUESTION, 0, IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL,
				WorkbenchMessages.EditorManager_openNewEditorLabel) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		int result = dialog.open();
			IEditorPart editor = dirtyEditor.getEditor(true);
			if (!page.saveEditor(editor, false)) {
				return null;
			}
		} else if ((result == 2) || (result == -1)) {
			return null;
		}
		return dirtyEditor;
