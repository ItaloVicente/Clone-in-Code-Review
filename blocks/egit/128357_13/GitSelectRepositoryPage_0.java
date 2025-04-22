	protected void createAddRepositoryButton(Composite tb) {
		addRepo = new Button(tb, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.FILL, SWT.BEGINNING).applyTo(addRepo);
		addRepo.setText(UIText.GitSelectRepositoryPage_AddButton);
		addRepo.setToolTipText(UIText.GitSelectRepositoryPage_AddTooltip);
		addRepo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				List<String> configuredDirs = util.getConfiguredRepositories();
				RepositorySearchWizard wizard = new RepositorySearchWizard(
						configuredDirs, allowBare);
				WizardDialog dlg = new WizardDialog(getShell(), wizard);
				if (dlg.open() == Window.OK
						&& !wizard.getDirectories().isEmpty()) {
					Set<String> dirs = wizard.getDirectories();
					for (String dir : dirs) {
						File gitDir = FileUtils.canonicalize(new File(dir));
						GerritUtil.tryToAutoConfigureForGerrit(gitDir);
						util.addConfiguredRepository(gitDir);
					}
					checkPage();
				}
			}

		});
	}

