			rememberEditorButton.addListener(SWT.Selection, event -> {
				if (rememberTypeButton != null && rememberEditorButton.getSelection()
						&& rememberTypeButton.getSelection()) {
					rememberTypeButton.setSelection(false);
				}
				updateEnableState();
			});
