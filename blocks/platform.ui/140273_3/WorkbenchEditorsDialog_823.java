		initializeDialogUnits(parent);

		Font font = parent.getFont();

		Composite dialogArea = new Composite(parent, SWT.NONE);
		CellLayout dialogAreaLayout = new CellLayout(1)
				.setMargins(convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN),
						convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN))
				.setSpacing(convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING),
						convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING))
				.setRow(1, Row.growing());
		dialogArea.setLayout(dialogAreaLayout);
		dialogArea.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label l = new Label(dialogArea, SWT.NONE);
		l.setText(WorkbenchMessages.WorkbenchEditorsDialog_label);
		l.setFont(font);
		l.setLayoutData(new CellData().align(SWT.FILL, SWT.CENTER));
		editorsTable = new Table(dialogArea, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		editorsTable.setLinesVisible(true);
		editorsTable.setHeaderVisible(true);
		editorsTable.setFont(font);

		final int height = 16 * editorsTable.getItemHeight();
		final int width = (int) (2.5 * height);

		CellData tableData = new CellData().align(SWT.FILL, SWT.FILL).setHint(CellData.OVERRIDE, width, height);

		editorsTable.setLayoutData(tableData);
		editorsTable.setLayout(new Layout() {
			@Override
			protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
				return new Point(width, height);
			}

			@Override
