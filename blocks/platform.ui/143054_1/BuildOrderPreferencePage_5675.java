		Object[] result = dialog.getResult();

		int currentItemsLength = currentItems.length;
		int resultLength = result.length;
		String[] newItems = new String[currentItemsLength + resultLength];

		System.arraycopy(currentItems, 0, newItems, 0, currentItemsLength);
		System
				.arraycopy(result, 0, newItems, currentItemsLength,
						result.length);
		this.buildList.setItems(newItems);
	}

	private void updateValidState() {
		setValid(maxItersField.isValid());
	}

	private void createBuildOrderList(Composite composite, boolean enabled) {

		Font font = composite.getFont();

		this.buildLabel = new Label(composite, SWT.NONE);
		this.buildLabel.setText(LIST_LABEL);
		this.buildLabel.setEnabled(enabled);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		this.buildLabel.setLayoutData(gridData);
		this.buildLabel.setFont(font);

		this.buildList = new List(composite, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		this.buildList.setEnabled(enabled);
		GridData data = new GridData();
		data.heightHint = buildList.getItemHeight();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		this.buildList.setLayoutData(data);
		this.buildList.setFont(font);
	}

	@Override
