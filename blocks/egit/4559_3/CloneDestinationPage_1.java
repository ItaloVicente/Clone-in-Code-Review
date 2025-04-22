	private void createProjectGroup(final Composite parent) {
		final Group group = createGroup(parent,
				"Projects"); //$NON-NLS-1$

		GridLayoutFactory.swtDefaults().applyTo(group);
		importProjectsButton = new Button(group, SWT.CHECK);
		importProjectsButton.setText(UIText.CloneDestinationPage_importButton);
		importProjectsButton.setSelection(Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(UIPreferences.CLONE_WIZARD_IMPORT_PROJECTS));
		importProjectsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(UIPreferences.CLONE_WIZARD_IMPORT_PROJECTS,
								importProjectsButton.getSelection());
			}
		});

		String[] workingSetTypes = new String[] {
				"org.eclipse.ui.resourceWorkingSetPage", //$NON-NLS-1$
				"org.eclipse.jdt.ui.JavaWorkingSetPage" //$NON-NLS-1$
		};
		workingSetGroup = new WorkingSetGroup(group, null, workingSetTypes);
	}

