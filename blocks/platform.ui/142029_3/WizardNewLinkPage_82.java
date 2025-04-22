		createLinkButton.setSelection(createLink);
		GridData data = new GridData();
		data.horizontalSpan = 3;
		createLinkButton.setLayoutData(data);
		createLinkButton.setFont(font);
		SelectionListener listener = new SelectionAdapter() {
			@Override
