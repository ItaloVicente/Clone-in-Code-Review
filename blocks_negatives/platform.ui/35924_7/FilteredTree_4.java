		if (showFilterControls) {
			if (useNativeSearchField(parent)) {
				filterComposite = new Composite(this, SWT.NONE);
			} else {
				filterComposite = new Composite(this, SWT.BORDER);
				filterComposite.setBackground(getDisplay().getSystemColor(
						SWT.COLOR_LIST_BACKGROUND));
			}
			GridLayout filterLayout = new GridLayout(2, false);
			filterLayout.marginHeight = 0;
			filterLayout.marginWidth = 0;
			filterComposite.setLayout(filterLayout);
			filterComposite.setFont(parent.getFont());

			createFilterControls(filterComposite);
			filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					true, false));
