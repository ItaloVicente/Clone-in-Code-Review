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
		Map<String, String> uniqueWorkspaceNames = createUniqueWorkspaceNameMap();
		for (Entry<String, String> uniqueWorkspaceEntry : uniqueWorkspaceNames.entrySet()) {
			final String recentWorkspace = uniqueWorkspaceEntry.getValue();

			Composite recentWorkspacePanel = new Composite(panel, SWT.NONE);
			recentWorkspacesComposites.put(recentWorkspace, recentWorkspacePanel);
			GridLayout recentWorkspacePanelLayout = new GridLayout(3, false);
			recentWorkspacePanel.setLayout(recentWorkspacePanelLayout);

			Link link = new Link(recentWorkspacePanel, SWT.WRAP);
			link.setLayoutData(new GridData(500, SWT.DEFAULT));
			link.setText("<a>" + uniqueWorkspaceEntry.getKey() + "</a>"); //$NON-NLS-1$//$NON-NLS-2$
			link.setToolTipText(recentWorkspace);

			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					workspaceSelected(recentWorkspace);
				}
			});

			Menu menu = new Menu(link);
			MenuItem forgetItem = new MenuItem(menu, SWT.PUSH);
			forgetItem.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_forgetWorkspace);
			forgetItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					forgetworkspaceSelected(recentWorkspace);
				}
			});

			link.addListener(SWT.MouseDown, event -> {
				if (event.button == 3) {
					menu.setVisible(true);
				}
			});

		}
	}

	private Map<String, String> createUniqueWorkspaceNameMap() {
		Map<String, String> uniqueWorkspaceNameMap = new HashMap<>();
		List<String[]> splittedWorkspaceNames = Arrays.asList(launchData.getRecentWorkspaces()).stream()
				.filter(s -> s != null && !s.isEmpty()).map(s -> s.split("\\\\"))//$NON-NLS-1$
				.collect(Collectors.toList());
		for (int i = 1; !splittedWorkspaceNames.isEmpty(); i++) {
			final int c = i;
			Function<String[], String> stringArraytoName = s -> String.join("\\", //$NON-NLS-1$
					Arrays.copyOfRange(s, s.length - c, s.length));
			List<String> uniqueNames = splittedWorkspaceNames.stream().map(stringArraytoName)
					.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet().stream()
					.filter(e -> e.getValue() == 1).map(e -> e.getKey()).collect(Collectors.toList());
			splittedWorkspaceNames.removeIf(a -> {
				String joined = stringArraytoName.apply(a);
				if (uniqueNames.contains(joined)) {
					uniqueWorkspaceNameMap.put(joined, String.join("\\", a)); //$NON-NLS-1$
					return true;
				}
				return false;
			});
		}
		return uniqueWorkspaceNameMap;
	}

