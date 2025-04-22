				Button variableButton = new Button(parent, SWT.PUSH);
				variableButton
						.setText(UIText.GitPreferenceRoot_DefaultRepoFolderVariableButton);

				variableButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(
								getShell());
						int returnCode = dialog.open();
						if (returnCode == Window.OK) {
							setStringValue(dialog.getVariableExpression());
						}
					}
				});
			}
