	private class DialogListener implements Listener {

		@Override
		public void handleEvent(Event event) {
			if (event.type == SWT.MouseDoubleClick) {
				handleDoubleClickEvent();
				return;
			}
			if (event.widget == externalButton) {
				fillEditorTable();
			} else if (event.widget == browseExternalEditorsButton) {
				promptForExternalEditor();
			} else if (event.widget == editorTableViewer.getTree()) {
				if (!editorTableViewer.getSelection().isEmpty()) {
					selectedEditor = (EditorDescriptor) editorTableViewer.getStructuredSelection().getFirstElement();
				} else {
					selectedEditor = null;
					okButton.setEnabled(false);
				}
			}
			if (rememberEditorButton != null && rememberTypeButton != null && rememberEditorButton.getSelection()
					&& rememberTypeButton.getSelection()) {
				if (event.widget == rememberEditorButton) {
					rememberTypeButton.setSelection(false);
				}
				if (event.widget == rememberTypeButton) {
					rememberEditorButton.setSelection(false);
				}
			}
			updateEnableState();
		}

	}

