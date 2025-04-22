		if (rememberEditorButton != null) {
			boolean selection = rememberEditorButton.getSelection();
			fileNameButton.setEnabled(selection);
			if (!getFileType().isEmpty()) {
				fileTypeButton.setEnabled(selection);
			}
		}
