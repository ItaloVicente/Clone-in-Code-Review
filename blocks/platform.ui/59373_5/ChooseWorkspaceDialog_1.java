	private void createRecentWorkspacesComposite(final Composite composite) {
		FormToolkit toolkit = new FormToolkit(composite.getDisplay());
		composite.addDisposeListener(c -> toolkit.dispose());
		recentWorkspacesForm = toolkit.createForm(composite);
		recentWorkspacesForm.setBackground(composite.getBackground());
		recentWorkspacesForm.getBody().setLayout(new GridLayout());
		ExpandableComposite expandableComposite = toolkit.createExpandableComposite(recentWorkspacesForm.getBody(),
				ExpandableComposite.TWISTIE);
		recentWorkspacesForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		expandableComposite.setBackground(composite.getBackground());
		expandableComposite.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_recentWorkspaces);
		expandableComposite.setExpanded(launchData.isOpenRecentWorkspacesComposite());
		expandableComposite.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				getShell().layout();
				initializeBounds();
				launchData.setOpenRecentWorkspacesComposite(((ExpandableComposite) e.getSource()).isExpanded());
			}
		});

		Composite panel = new Composite(expandableComposite, SWT.NONE);
		expandableComposite.setClient(panel);
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		panel.setLayout(layout);
		recentWorkspacesComposites = new HashMap<>(launchData.getRecentWorkspaces().length);
		for (int i = 0; i < launchData.getRecentWorkspaces().length; i++) {
			final String recentWorkspace = launchData.getRecentWorkspaces()[i];
			if (recentWorkspace == null || recentWorkspace.equals(launchData.getSelection())) {
				continue;
			}

			Composite recentWorkspacePanel = new Composite(panel, SWT.NONE);
			recentWorkspacesComposites.put(recentWorkspace, recentWorkspacePanel);
			GridLayout recentWorkspacePanelLayout = new GridLayout(3, false);
			recentWorkspacePanel.setLayout(recentWorkspacePanelLayout);

			Label label = new Label(recentWorkspacePanel, SWT.WRAP);
			label.setLayoutData(new GridData(490, SWT.DEFAULT));
			label.setText(recentWorkspace);

			Button startWorkspacebutton = new Button(recentWorkspacePanel, SWT.PUSH);
			startWorkspacebutton.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_launchWorkspace);
			startWorkspacebutton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					workspaceSelected(recentWorkspace);
				}
			});

			Button forgetWorkspacebutton = new Button(recentWorkspacePanel, SWT.PUSH);
			forgetWorkspacebutton.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_forgetWorkspace);
			forgetWorkspacebutton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					forgetworkspaceSelected(recentWorkspace);
				}

			});
		}
    }

