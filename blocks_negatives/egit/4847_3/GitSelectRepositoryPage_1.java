		cloneRepo = new Button(tb, SWT.PUSH);
		cloneRepo.setText(UIText.GitSelectRepositoryPage_CloneButton);
		cloneRepo.setToolTipText(UIText.GitSelectRepositoryPage_CloneTooltip);

		GridDataFactory.fillDefaults().grab(false, false).align(SWT.FILL,
				SWT.BEGINNING).applyTo(cloneRepo);

		cloneRepo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				GitCloneWizard cloneWizard = new GitCloneWizard();
				cloneWizard.setCallerRunsCloneOperation(true);
				WizardDialog dlg = new WizardDialog(getShell(), cloneWizard);
				dlg.setHelpAvailable(true);
				if (dlg.open() == Window.OK)
					cloneWizard.runCloneOperation(getContainer());
				checkPage();
			}

		});

