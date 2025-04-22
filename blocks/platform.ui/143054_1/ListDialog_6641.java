		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = convertHeightInCharsToPixels(heightInChars);
		gd.widthHint = convertWidthInCharsToPixels(widthInChars);
		Table table = fTableViewer.getTable();
		table.setLayoutData(gd);
		table.setFont(container.getFont());
		return parent;
	}

	protected int getTableStyle() {
		return SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
	}

	@Override
