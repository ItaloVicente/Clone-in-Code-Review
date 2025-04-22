		Font font = parent.getFont();

		initializeDialogUnits(parent);

		Composite pageComponent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		pageComponent.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = SIZING_SELECTION_PANE_WIDTH;
		pageComponent.setLayoutData(data);
		pageComponent.setFont(font);

		Label variableLabel = new Label(pageComponent, SWT.LEFT);
		variableLabel.setText(NLS
