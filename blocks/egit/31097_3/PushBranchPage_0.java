		Link advancedDialogLink = new Link(main, SWT.NONE);
		advancedDialogLink.setText(UIText.PushBranchPage_advancedWizardLink);
		advancedDialogLink
				.setToolTipText(UIText.PushBranchPage_advancedWizardLinkTooltip);
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
