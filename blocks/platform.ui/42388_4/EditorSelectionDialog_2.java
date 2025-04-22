		if (fileName != null) {

			rememberEditorButton = new Button(contents, SWT.CHECK | SWT.LEFT);
			rememberEditorButton.setText(WorkbenchMessages.EditorSelection_rememberEditor);
			rememberEditorButton.addListener(SWT.Selection, listener);
			data = new GridData();
			data.horizontalSpan = 2;
			rememberEditorButton.setLayoutData(data);
			rememberEditorButton.setFont(font);

			group = new Composite(contents, SWT.SHADOW_NONE);
			group.setLayout(new GridLayout(2, false));
			data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = SWT.FILL;
			data.horizontalSpan = 2;
			data.horizontalIndent = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
			group.setLayoutData(data);

			String fileType = getFileType();
			if (!fileType.isEmpty()) {
				fileTypeButton = new Button(group, SWT.RADIO | SWT.LEFT);
				fileTypeButton.setText("*." + fileType); //$NON-NLS-1$
				fileTypeButton.addListener(SWT.Selection, listener);
				data = new GridData();
				data.horizontalSpan = 1;
				data.grabExcessHorizontalSpace = true;
				fileTypeButton.setLayoutData(data);
				fileTypeButton.setFont(font);
				fileTypeButton.setSelection(true);
				fileTypeButton.setEnabled(false);
			}

			fileNameButton = new Button(group, SWT.RADIO | SWT.LEFT);
			fileNameButton.setText(fileName);
			fileNameButton.addListener(SWT.Selection, listener);
			data = new GridData();
			data.horizontalSpan = 1;
			data.grabExcessHorizontalSpace = true;
			fileNameButton.setLayoutData(data);
			fileNameButton.setFont(font);
			fileNameButton.setEnabled(false);
			if (fileType.isEmpty()) {
				fileNameButton.setSelection(true);
			}
		}

