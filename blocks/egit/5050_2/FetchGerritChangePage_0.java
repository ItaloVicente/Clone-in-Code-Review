		warningAdditionalRefNotActive = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).exclude(true).applyTo(warningAdditionalRefNotActive);
		warningAdditionalRefNotActive.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(
				warningAdditionalRefNotActive);
		warningAdditionalRefNotActive.setVisible(false);

		activateAdditionalRefs = new Button(warningAdditionalRefNotActive,
				SWT.CHECK);
		activateAdditionalRefs
				.setText(UIText.FetchGerritChangePage_ActivateAdditionalRefsButton);
		activateAdditionalRefs
				.setToolTipText(UIText.FetchGerritChangePage_ActivateAdditionalRefsTooltip);

