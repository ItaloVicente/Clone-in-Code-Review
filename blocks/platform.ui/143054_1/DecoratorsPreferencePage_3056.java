		Font font = parent.getFont();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IWorkbenchHelpContextIds.DECORATORS_PREFERENCE_PAGE);

		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		mainComposite.setFont(font);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 10;
		mainComposite.setLayout(layout);

		createDecoratorsArea(mainComposite);
		createDescriptionArea(mainComposite);
		populateDecorators();

		return mainComposite;
	}

	private void createDecoratorsArea(Composite mainComposite) {

		Font mainFont = mainComposite.getFont();
		Composite decoratorsComposite = new Composite(mainComposite, SWT.NONE);
		decoratorsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout decoratorsLayout = new GridLayout();
		decoratorsLayout.marginWidth = 0;
		decoratorsLayout.marginHeight = 0;
		decoratorsComposite.setLayout(decoratorsLayout);
		decoratorsComposite.setFont(mainFont);

		Label decoratorsLabel = new Label(decoratorsComposite, SWT.NONE);
		decoratorsLabel.setText(WorkbenchMessages.DecoratorsPreferencePage_decoratorsLabel);
		decoratorsLabel.setFont(mainFont);

		checkboxViewer = CheckboxTableViewer.newCheckList(decoratorsComposite, SWT.SINGLE | SWT.TOP | SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.heightHint = 300;
		checkboxViewer.getTable().setLayoutData(layoutData);
		checkboxViewer.getTable().setFont(decoratorsComposite.getFont());
		checkboxViewer.setLabelProvider(new LabelProvider() {
			@Override
