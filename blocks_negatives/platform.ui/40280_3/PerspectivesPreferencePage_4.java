	/**
	 * Creates a composite that contains buttons for selecting open view mode.
	 * 
	 * @param composite
	 *            the parent composite
	 */
	protected void createOpenViewButtonGroup(Composite composite) {

		Font font = composite.getFont();

		Group buttonComposite = new Group(composite, SWT.LEFT);
		buttonComposite.setText(FVG_TITLE);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonComposite.setFont(composite.getFont());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		buttonComposite.setLayout(layout);

		openViewModeLabel = new Label(buttonComposite, SWT.NONE);
		openViewModeLabel.setText(OVM_TITLE);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		openViewModeLabel.setLayoutData(data);

		openEmbedButton = new Button(buttonComposite, SWT.RADIO);
		openEmbedButton.setText(OVM_EMBED);
		openEmbedButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_EMBED);
		openEmbedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openViewMode = IPreferenceConstants.OVM_EMBED;
			}
		});
		openEmbedButton.setFont(font);

		if (openViewMode == IPreferenceConstants.OVM_FLOAT) {
			openViewMode = IPreferenceConstants.OVM_FAST;
		}

		openFastButton = new Button(buttonComposite, SWT.RADIO);
		openFastButton.setText(OVM_FAST);
		openFastButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_FAST);
		openFastButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openViewMode = IPreferenceConstants.OVM_FAST;
			}
		});
		openFastButton.setFont(font);

		createFVBHideButton(buttonComposite);
	}

	protected void createFVBHideButton(Composite composite) {
		if (!isFVBConfigured)
			return;
		Font font = composite.getFont();
		fvbHideButton = new Button(composite, SWT.CHECK);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		fvbHideButton.setLayoutData(data);
		fvbHideButton.setText(FVB_HIDE);

		fvbHideButton.setSelection(this.getPreferenceStore().getBoolean(
				IPreferenceConstants.FVB_HIDE));
		fvbHideButton.setFont(font);
	}

