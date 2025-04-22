		separateButton = new Button(main, SWT.CHECK);
		separateButton
				.setText(UIText.CreateRepositoryPage_SeparateGitDirCheckbox);
		GridDataFactory.fillDefaults().indent(10, 0).span(3, 1)
				.applyTo(separateButton);
		separateButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateControls();
				checkPage();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateControls();
				checkPage();
			}
		});
