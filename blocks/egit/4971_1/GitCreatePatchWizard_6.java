			wsRadio = new Button(composite, SWT.RADIO);
			wsRadio.setText(UIText.GitCreatePatchWizard_Workspace);
			wsRadio.setSelection(false);

			wsPathText = new Text(composite, SWT.BORDER);
			wsPathText.setLayoutData(gd);
			wsPathText.setEnabled(false);

			wsBrowseButton = new Button(composite, SWT.PUSH);
			wsBrowseButton.setText(UIText.GitCreatePatchWizard_Browse);
			wsBrowseButton.setLayoutData(data);
			wsBrowseButton.setEnabled(false);

