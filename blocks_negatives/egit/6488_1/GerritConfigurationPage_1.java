		UIUtils.setEnabledRecursively(pushConfigurationGroup,
				configureGerrit());
	}

	private void createGerritCheckbox(Composite panel) {
		Composite comp = SWTUtils.createHFillComposite(panel,
				SWTUtils.MARGINS_NONE, 1);
		configureGerrit = new Button(comp, SWT.CHECK);
		configureGerrit.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateEnablement();
				checkPage();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				updateEnablement();
				checkPage();
			}
		});
		configureGerrit
				.setText(UIText.GerritConfigurationPage_configurePushToGerrit);
