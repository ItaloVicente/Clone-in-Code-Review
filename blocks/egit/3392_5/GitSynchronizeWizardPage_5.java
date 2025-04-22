		buttonsComposite.setLayoutData(GridDataFactory.fillDefaults().grab(true, false)
				.create());

		final Button includeLocal = new Button(buttonsComposite, SWT.CHECK);
		includeLocal
				.setText(UIText.GitBranchSynchronizeWizardPage_includeUncommitedChanges);
		includeLocal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shouldIncludeLocal = includeLocal.getSelection();
			}
		});
		includeLocal.setLayoutData(GridDataFactory.fillDefaults().grab(true, false)
				.create());
