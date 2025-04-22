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
