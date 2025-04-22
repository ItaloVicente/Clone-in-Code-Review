		normalizeButton = new Button(nameComposite, SWT.NONE);
		normalizeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String result = Repository
						.normalizeBranchName(nameText.getText());
				nameText.setText(result);
			}
		});
		normalizeButton
				.setImage(UIIcons.getImage(resourceManager, UIIcons.NORMALIZE));
		normalizeButton.setToolTipText(UIText.CreateBranchPage_NormalizName);

