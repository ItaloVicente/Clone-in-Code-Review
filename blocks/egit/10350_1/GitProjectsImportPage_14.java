	private void createOptionsArea(Composite workArea) {
		Composite optionsGroup = new Composite(workArea, SWT.NONE);
		optionsGroup.setLayout(new GridLayout());
		optionsGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		nestedProjectsCheckbox = new Button(workArea, SWT.CHECK);
		nestedProjectsCheckbox
				.setText(UIText.GitProjectsImportPage_SearchForNestedProjects);
		nestedProjectsCheckbox.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		nestedProjectsCheckbox.setSelection(nestedProjects);
		nestedProjectsCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nestedProjects = nestedProjectsCheckbox.getSelection();
				setProjectsList(lastPath);
			}
		});
	}

