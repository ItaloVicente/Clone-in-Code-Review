			if (rememberEditorButton != null && rememberTypeButton != null && rememberEditorButton.getSelection()
					&& rememberTypeButton.getSelection()) {
				if (event.widget == rememberEditorButton) {
					rememberTypeButton.setSelection(false);
				}
				if (event.widget == rememberTypeButton) {
					rememberEditorButton.setSelection(false);
				}
			}
