		GridLayout filterLayout = new GridLayout(2, false);
		filterLayout.marginHeight = 0;
		filterLayout.marginWidth = 0;
		filterComposite.setLayout(filterLayout);
		filterComposite.setFont(parent.getFont());

		createFilterControls(filterComposite);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		filterComposite.setVisible(isShowFilterControls());
		gridData.exclude = !isShowFilterControls();
		filterComposite.setLayoutData(gridData);
