			activitiesToEnable.addAll(activityIdsCopy);

			viewer.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			viewer.getControl().setFont(dialogFont);

			text = new Label(composite, SWT.NONE);
			text.setText(strings.getProperty(WorkbenchTriggerPointAdvisor.PROCEED_MULTI,
					RESOURCE_BUNDLE.getString(WorkbenchTriggerPointAdvisor.PROCEED_MULTI)));
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			text.setFont(dialogFont);
		}
		Label seperator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		seperator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		dontAskButton = new Button(composite, SWT.CHECK);
		dontAskButton.setSelection(false);
		dontAskButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dontAskButton.setText(strings.getProperty(WorkbenchTriggerPointAdvisor.DONT_ASK,
				RESOURCE_BUNDLE.getString(WorkbenchTriggerPointAdvisor.DONT_ASK)));
		dontAskButton.setFont(dialogFont);

		detailsComposite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		detailsComposite.setLayout(layout);
		detailsLabel = new Label(detailsComposite, SWT.NONE);
		detailsLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detailsLabel.setFont(dialogFont);

		detailsText = new Text(detailsComposite, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		detailsText.setLayoutData(new GridData(GridData.FILL_BOTH));
		detailsText.setFont(dialogFont);

		setDetails();

		GridData data = new GridData(GridData.FILL_BOTH);
		detailsComposite.setLayoutData(data);
		setDetailHints();

		return composite;
	}

	protected void setDetails() {
		if (selectedActivity == null) {
			detailsLabel.setText(strings.getProperty(WorkbenchTriggerPointAdvisor.NO_DETAILS,
					RESOURCE_BUNDLE.getString(WorkbenchTriggerPointAdvisor.NO_DETAILS)));
			detailsText.setText(""); //$NON-NLS-1$
		} else {
			IActivity activity = PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
					.getActivity(selectedActivity);
			String name;
			try {
				name = activity.getName();
			} catch (NotDefinedException e1) {
				name = selectedActivity;
			}
			String desc;
			try {
				desc = activity.getDescription();
			} catch (NotDefinedException e) {
				desc = RESOURCE_BUNDLE.getString("noDescAvailable"); //$NON-NLS-1$
			}
			detailsLabel.setText(MessageFormat.format(RESOURCE_BUNDLE.getString("detailsLabel"), name)); //$NON-NLS-1$
			detailsText.setText(desc);
		}
	}

	protected void setDetailHints() {
		GridData data = (GridData) detailsComposite.getLayoutData();
		if (showDetails) {
			Composite parent = detailsComposite.getParent();
			data.widthHint = parent.getSize().x - ((GridLayout) parent.getLayout()).marginWidth * 2;
			data.heightHint = convertHeightInCharsToPixels(5);
		} else {
			data.widthHint = 0;
			data.heightHint = 0;
		}
	}

	private void setDetailButtonLabel() {
		if (!showDetails) {
