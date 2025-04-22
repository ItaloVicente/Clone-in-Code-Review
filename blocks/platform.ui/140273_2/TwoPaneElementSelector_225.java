		Label label = new Label(parent, SWT.NONE);
		label.setText(name);
		label.setFont(parent.getFont());
		return label;
	}

	protected Table createLowerList(Composite parent) {
		Table list = new Table(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		list.addListener(SWT.Selection, evt -> handleLowerSelectionChanged());
		list.addListener(SWT.MouseDoubleClick, evt -> handleDefaultSelected());
		list.addDisposeListener(e -> fQualifierRenderer.dispose());
		GridData data = new GridData();
		data.widthHint = convertWidthInCharsToPixels(50);
		data.heightHint = convertHeightInCharsToPixels(5);
		data.grabExcessVerticalSpace = true;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		list.setLayoutData(data);
		list.setFont(parent.getFont());
		fLowerList = list;
		return list;
	}

	@Override
