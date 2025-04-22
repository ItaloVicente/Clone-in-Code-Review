		Group afterImportAction = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(
				afterImportAction);
		afterImportAction
				.setText(UIText.GitSelectWizardPage_SharingProjectsHeader);
		afterImportAction.setLayout(new GridLayout(1, false));

		actionAutoShare = new Button(afterImportAction, SWT.RADIO);
		actionAutoShare.setText(UIText.GitSelectWizardPage_AutoShareButton);
		actionAutoShare.addSelectionListener(sl);

		actionNothing = new Button(afterImportAction, SWT.RADIO);
		actionNothing.setText(UIText.GitSelectWizardPage_NoShareButton);
		actionNothing.addSelectionListener(sl);

