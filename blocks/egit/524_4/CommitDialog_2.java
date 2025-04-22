		changeIdButton = new Button(container, SWT.CHECK);
		changeIdButton.setText(UIText.CommitDialog_AddChangeIdLabel);
		changeIdButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		changeIdButton.setToolTipText(UIText.CommitDialog_AddChangeIdTooltip);
		changeIdButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				createChangeId = changeIdButton.getSelection();
				String text = commitText.getText();
				if (createChangeId) {
					String changedText = ChangeIdUtil.insertId(text,
							originalChangeId != null ? originalChangeId : ObjectId.zeroId());
					if (!text.equals(changedText))
						commitText.setText(changedText);
				} else {
					String cleanedText = text.replaceAll(
							"\\nChange-Id:.*?\\n", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
					if (!text.equals(cleanedText))
						commitText.setText(cleanedText);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

