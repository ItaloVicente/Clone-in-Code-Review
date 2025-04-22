		changeIdButton = new Button(container, SWT.CHECK);
		changeIdButton.setText(UIText.CommitDialog_AddChangeIdLabel);
		changeIdButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		changeIdButton.setToolTipText(UIText.CommitDialog_AddChangeIdTooltip);
		changeIdButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				createChangeId = changeIdButton.getSelection();
				String text = commitText.getText().replaceAll(Text.DELIMITER, "\n"); //$NON-NLS-1$
				if (createChangeId) {
					String changedText = ChangeIdUtil.insertId(text,
							originalChangeId != null ? originalChangeId : ObjectId.zeroId());
					if (!text.equals(changedText)) {
						changedText = changedText.replaceAll("\n", Text.DELIMITER); //$NON-NLS-1$
						commitText.setText(changedText);
					}
				} else {
					int changeIdOffset = findOffsetOfChangeIdLine(text);
					if (changeIdOffset > 0) {
						int endOfChangeId = findNextEOL(changeIdOffset, text);
						String cleanedText = text.substring(0, changeIdOffset)
								+ text.substring(endOfChangeId);
						cleanedText = cleanedText.replaceAll("\n", Text.DELIMITER); //$NON-NLS-1$
						commitText.setText(cleanedText);
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

