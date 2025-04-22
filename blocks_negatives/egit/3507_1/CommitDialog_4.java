					if (previousAuthor != null)
						authorText.setText(previousAuthor);
				}
				refreshChangeIdText();
			}
		});

		amendingItem.setToolTipText(UIText.CommitDialog_AmendPreviousCommit);
		Image amendImage = UIIcons.AMEND_COMMIT.createImage();
		UIUtils.hookDisposal(amendingItem, amendImage);
		amendingItem.setImage(amendImage);

		signedOffItem = new ToolItem(messageToolbar, SWT.CHECK);
		signedOffItem.setSelection(signedOff);
		if (!amending)
			refreshSignedOffBy();
		signedOffItem.setToolTipText(UIText.CommitDialog_AddSOB);
		Image signedOffImage = UIIcons.SIGNED_OFF.createImage();
		UIUtils.hookDisposal(signedOffItem, signedOffImage);
		signedOffItem.setImage(signedOffImage);

		signedOffItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				refreshSignedOffBy();
			}

		});

		changeIdItem = new ToolItem(messageToolbar, SWT.CHECK);
		Image changeIdImage = UIIcons.GERRIT.createImage();
		UIUtils.hookDisposal(changeIdItem, changeIdImage);
		changeIdItem.setImage(changeIdImage);
		changeIdItem.setToolTipText(UIText.CommitDialog_AddChangeIdLabel);
		changeIdItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				refreshChangeIdText();
			}

		});

		changeIdItem.setSelection(createChangeIdDefault);
		if (!amending)
			refreshChangeIdText();

		commitText.getTextWidget().addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateSignedOffButton();
				updateChangeIdButton();
