		if (agnosticSymLink != null) {
		deleteAgnosticSymLink = new Button(main, SWT.CHECK);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(deleteAgnosticSymLink);
		deleteAgnosticSymLink.setText(
				UIText.DeleteRepositoryConfirmDialog_DeleteSymLinkCheckbox);
		deleteAgnosticSymLinkLabel = createIndentedLabel(main,
				agnosticSymLink.getLocation().toOSString());

		deleteAgnosticSymLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shouldDeleteAgnosticSymLink = deleteAgnosticSymLink
						.getSelection();
				updateUI();
			}
		});

		}
