		}

		return false;
	}

	public void activate() {
		page.setDescription(WorkbenchMessages.NewWizardNewPage_description);
	}

	protected Control createControl(Composite parent) {

		Font wizardFont = parent.getFont();
		Composite outerContainer = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		outerContainer.setLayout(layout);

		Label wizardLabel = new Label(outerContainer, SWT.NONE);
		GridData data = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
		outerContainer.setLayoutData(data);
		wizardLabel.setFont(wizardFont);
		wizardLabel.setText(WorkbenchMessages.NewWizardNewPage_wizardsLabel);

		Composite innerContainer = new Composite(outerContainer, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		innerContainer.setLayout(layout);
		innerContainer.setFont(wizardFont);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		innerContainer.setLayoutData(data);

		filteredTree = createFilteredTree(innerContainer);
		createOptionsButtons(innerContainer);

		createImage(innerContainer);

		updateDescription(null);

		restoreWidgetValues();

		return outerContainer;
	}

	protected FilteredTree createFilteredTree(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = SIZING_VIEWER_WIDTH;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;

		boolean needsHint = DialogUtil.inRegularFontMode(parent);

		if (needsHint) {
			data.heightHint = SIZING_LISTS_HEIGHT;
		}
		composite.setLayoutData(data);

		filteredTreeFilter = new WizardPatternFilter();
		FilteredTree filterTree = new FilteredTree(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER,
				filteredTreeFilter, true);
