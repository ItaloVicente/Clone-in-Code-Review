		Font font = parent.getFont();

		Composite outerContainer = new Composite(parent, SWT.NONE);
		outerContainer.setLayout(new GridLayout());
		outerContainer.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		outerContainer.setFont(font);

		Label messageLabel = new Label(outerContainer, SWT.NONE);
		messageLabel.setText(message);
		messageLabel.setFont(font);

		createViewer(outerContainer);
		layoutTopControl(viewer.getControl());

		restoreWidgetValues();

		setControl(outerContainer);
	}

	private void createViewer(Composite parent) {
		Table table = new Table(parent, SWT.BORDER);
		table.setFont(parent.getFont());

		viewer = new TableViewer(table);
		viewer.setContentProvider(new WizardContentProvider());
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		viewer.setComparator(new WorkbenchViewerComparator());
		viewer.addSelectionChangedListener(this);
		viewer.addDoubleClickListener(this);
		viewer.setInput(wizardElements);
	}

	protected abstract IWizardNode createWizardNode(WorkbenchWizardElement element);

	@Override
