		Link advancedDialogLink = new Link(main, SWT.NONE);
		advancedDialogLink
				.setText("<A>" + UIText.PushBranchPage_advancedWizard + "</A>"); //$NON-NLS-1$ //$NON-NLS-2$
		advancedDialogLink.setLayoutData(new GridData(SWT.END, SWT.END, false,
				true));
		advancedDialogLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell parentShell = getShell().getParent().getShell();
				PushWizard advancedWizard = null;
				try {
					advancedWizard = new PushWizard(repository);
					getShell().close();
					new WizardDialog(parentShell, advancedWizard).open();
				} catch (URISyntaxException ex) {
					Activator.logError(ex.getMessage(), ex);
				}
