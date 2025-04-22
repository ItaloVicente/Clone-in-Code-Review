				rememberTypeButton.addListener(SWT.Selection, event -> {
					if (rememberEditorButton != null && rememberEditorButton.getSelection()
							&& rememberTypeButton.getSelection()) {
						rememberEditorButton.setSelection(false);
					}
					updateEnableState();
				});
