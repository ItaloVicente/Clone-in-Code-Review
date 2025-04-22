		final RepositoryUtil util = Activator.getDefault().getRepositoryUtil();
		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(main);

		final Button internalModeButton = new Button(main, SWT.CHECK);
		internalModeButton
				.setText(UIText.ExistingOrNewPage_InternalModeCheckbox);
		internalModeButton
				.setToolTipText(UIText.ExistingOrNewPage_CreationInWorkspaceWarningTooltip);
		internalModeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				internalMode = internalModeButton.getSelection();
				updateControls();
			}
		});

		externalComposite = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(externalComposite);
		externalComposite.setLayout(new GridLayout(3, false));

		new Label(externalComposite, SWT.NONE)
				.setText(UIText.ExistingOrNewPage_ExistingRepositoryLabel);
		final Combo existingRepoCombo = new Combo(externalComposite,
				SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(existingRepoCombo);
		final ComboViewer v = new ComboViewer(existingRepoCombo);
		v.setContentProvider(new RepoComboContentProvider());
		v.setLabelProvider(new RepoComboLabelProvider());
		v.setInput(new Object());
		Repository first = (Repository) v.getElementAt(0);
		if (first != null) {
			v.setSelection(new StructuredSelection(first));
			this.selectedRepository = (Repository) v.getElementAt(0);
		}

		existingRepoCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedRepository = null;
				IStructuredSelection sel = (IStructuredSelection) v
						.getSelection();
				setRepository((Repository) sel.getFirstElement());
				updateControls();
			}
		});

		Button newRepo = new Button(externalComposite, SWT.PUSH);
		newRepo.setText(UIText.ExistingOrNewPage_CreateRepositoryButton);
		newRepo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				NewRepositoryWizard wiz = new NewRepositoryWizard(true);
				if (new WizardDialog(getShell(), wiz).open() == Window.OK) {
					v.refresh();
					selectedRepository = wiz.getCreatedRepository();
					v.setSelection(new StructuredSelection(selectedRepository));
					updateControls();
				}
			}
		});

		new Label(externalComposite, SWT.NONE)
				.setText(UIText.ExistingOrNewPage_WorkingDirectoryLabel);
		workDir = new Text(externalComposite, SWT.BORDER | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(workDir);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(workDir);

		new Label(externalComposite, SWT.NONE)
				.setText(UIText.ExistingOrNewPage_RelativePathLabel);
		relPath = new Text(externalComposite, SWT.BORDER);
		relPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateControls();
			}
		});

		GridDataFactory.fillDefaults().grab(true, false).applyTo(relPath);
		browseRepository = new Button(externalComposite, SWT.PUSH);
		browseRepository.setEnabled(false);
		browseRepository
				.setText(UIText.ExistingOrNewPage_BrowseRepositoryButton);
		browseRepository.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				dlg.setFilterPath(selectedRepository.getWorkTree().getPath());
				setRelativePath(dlg.open());
				updateControls();
			}
		});

		Table projectMoveTable = new Table(externalComposite, SWT.MULTI
				| SWT.FULL_SELECTION | SWT.CHECK);
		projectMoveViewer = new CheckboxTableViewer(projectMoveTable);
		GridDataFactory.fillDefaults().span(3, 1).grab(true, true)
				.applyTo(projectMoveTable);

		TableColumn tc;
		tc = new TableColumn(projectMoveTable, SWT.NONE);
		tc.setText(UIText.ExistingOrNewPage_ProjectNameColumnHeader);
		tc.setWidth(150);

		tc = new TableColumn(projectMoveTable, SWT.NONE);
		tc.setText(UIText.ExistingOrNewPage_CurrentLocationColumnHeader);
		tc.setWidth(250);

		tc = new TableColumn(projectMoveTable, SWT.NONE);
		tc.setText(UIText.ExistingOrNewPage_NewLocationTargetHeader);
		tc.setWidth(350);

		projectMoveTable.setHeaderVisible(true);
		projectMoveViewer
				.setContentProvider(ArrayContentProvider.getInstance());
		projectMoveViewer.setLabelProvider(moveProjectsLabelProvider);
		projectMoveViewer.setInput(myWizard.projects);
		projectMoveViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				updateControls();
			}
		});
		projectMoveViewer.setAllChecked(true);

		parentRepoComposite = new Composite(main, SWT.NONE);
		parentRepoComposite.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(parentRepoComposite);

		tree = new Tree(parentRepoComposite, SWT.BORDER | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.CHECK);
