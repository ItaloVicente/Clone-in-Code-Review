		final RepositoryUtil util = Activator.getDefault().getRepositoryUtil();
		Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout());
		main.setLayoutData(GridDataFactory.fillDefaults().grab(true, true)
				.create());

		Composite buttons = new Composite(main, SWT.NONE);
		buttons.setLayout(new GridLayout());

		Button parentFolderRepo = new Button(buttons, SWT.RADIO);
		parentFolderRepo
				.setText("Create or use Repository in parent folder of project"); //$NON-NLS-1$
		parentFolderRepo.setSelection(true);
		parentFolderRepo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				externalMode = false;
				updateControls();
			}
		});

		Button externalRepo = new Button(buttons, SWT.RADIO);
		externalRepo.setText("Use another existing Repository"); //$NON-NLS-1$
		externalRepo.setSelection(false);
		externalRepo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				externalMode = true;
				updateControls();
			}
		});

		externalComposite = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(externalComposite);
		externalComposite.setLayout(new GridLayout(3, false));

		new Label(externalComposite, SWT.NONE).setText("Repository:"); //$NON-NLS-1$
		final Combo existingRepoCombo = new Combo(externalComposite,
				SWT.READ_ONLY);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.applyTo(existingRepoCombo);
		final ComboViewer v = new ComboViewer(existingRepoCombo);
		v.setContentProvider(ArrayContentProvider.getInstance());
		v.setLabelProvider(new RepoComboLabelProvider());

		RepositoryCache cache = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache();
		GridDataFactory.fillDefaults().span(2, 1).applyTo(existingRepoCombo);
		List<Repository> nonBareRepos = new ArrayList<Repository>();
		for (String dir : util.getConfiguredRepositories()) {
			Repository repo;
			try {
				repo = cache.lookupRepository(new File(dir));
			} catch (IOException e1) {
				continue;
			}
			if (repo.isBare())
				continue;
			nonBareRepos.add(repo);
		}
		v.setInput(nonBareRepos);
		existingRepoCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedRepository = null;
				IStructuredSelection sel = (IStructuredSelection) v
						.getSelection();
				Object selected = sel.getFirstElement();
				if (selected instanceof Repository)
					setRepository((Repository) selected);
				updateControls();
			}
		});

		new Label(externalComposite, SWT.NONE).setText("Working directory:"); //$NON-NLS-1$
		workDir = new Text(externalComposite, SWT.BORDER | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(workDir);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(workDir);

		new Label(externalComposite, SWT.NONE)
				.setText("Path within Repository:"); //$NON-NLS-1$
		relPath = new Text(externalComposite, SWT.BORDER);
		relPath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateControls();
			}
		});
		GridDataFactory.fillDefaults().grab(true, false).applyTo(relPath);
		browseRepository = new Button(externalComposite, SWT.PUSH);
		browseRepository.setEnabled(false);
		browseRepository.setText("Browse..."); //$NON-NLS-1$
		browseRepository.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				dlg.setFilterPath(selectedRepository.getWorkTree().getPath());
				setRelativePath(dlg.open());
				updateControls();
			}
		});

		Table table = new Table(externalComposite, SWT.MULTI
				| SWT.FULL_SELECTION | SWT.CHECK);
		projectMoveViewer = new CheckboxTableViewer(table);
		GridDataFactory.fillDefaults().span(3, 1).grab(true, true)
				.applyTo(table);

		TableColumn tc;
		tc = new TableColumn(table, SWT.NONE);
		tc.setText("Project name"); //$NON-NLS-1$
		tc.setWidth(150);

		tc = new TableColumn(table, SWT.NONE);
		tc.setText("Current Location"); //$NON-NLS-1$
		tc.setWidth(250);

		tc = new TableColumn(table, SWT.NONE);
		tc.setText("New Location"); //$NON-NLS-1$
		tc.setWidth(350);

		table.setHeaderVisible(true);
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
