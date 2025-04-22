		setResult(null);
		super.cancelPressed();
	}

	protected FilteredList createFilteredList(Composite parent) {
		int flags = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | (fIsMultipleSelection ? SWT.MULTI : SWT.SINGLE);

		FilteredList list = new FilteredList(parent, flags, fRenderer, fIgnoreCase, fAllowDuplicates,
				fMatchEmptyString);

		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = convertHeightInCharsToPixels(fHeight);
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		list.setLayoutData(data);
		list.setFont(parent.getFont());
		list.setFilter((fFilter == null ? "" : fFilter)); //$NON-NLS-1$

		list.addSelectionListener(new SelectionListener() {
			@Override
