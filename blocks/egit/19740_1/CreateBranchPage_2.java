		sourceLabel.setText(UIText.CreateBranchPage_SourceLabel);
		sourceLabel.setToolTipText(UIText.CreateBranchPage_SourceTooltip);

		sourceIcon = new Label(main, SWT.NONE);
		sourceIcon.setImage(UIIcons.getImage(resourceManager, UIIcons.BRANCH));
		sourceIcon.setLayoutData(GridDataFactory.fillDefaults()
				.align(SWT.END, SWT.CENTER).create());

		sourceNameLabel = new Label(main, SWT.NONE);
		sourceNameLabel.setLayoutData(GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.CENTER)
				.grab(true, false).create());

		Button selectButton = new Button(main, SWT.NONE);
		selectButton.setText(UIText.CreateBranchPage_SourceSelectButton);
		selectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectSource();
			}
		});
		UIUtils.setButtonLayoutData(selectButton);
