		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setFont(parent.getFont());

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setBackground(container.getBackground());

		Group filtersComposite = new Group(composite, SWT.NONE);
		filtersComposite
				.setText(
						UIText.GitHistoryPage_filterRefDialog_filtersCompositLabel);

		filtersComposite.setLayout(new GridLayout(2, false));
		filtersComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		filtersComposite.setBackground(composite.getBackground());

		fillFiltersComposite(filtersComposite);
