
		Composite repoTab = SWTUtils.createHVFillComposite(tabFolder, SWTUtils.MARGINS_DEFAULT, 2);

		Label repoLabel = new Label(repoTab, SWT.NONE);
		repoLabel.setText(UIText.GlobalConfigurationPreferencePage_repositorySettingRepositoryLabel);

		Combo repoCombo = new Combo(repoTab, SWT.READ_ONLY);
		repoCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.widget;
				showRepositoryConfiguration(combo.getSelectionIndex());
			}
		});
		repoCombo.setItems(getRepositoryComboItems());

		repoConfigComposite = SWTUtils.createHVFillComposite(repoTab, SWT.NONE);
		repoConfigComposite.setLayoutData(SWTUtils.createHVFillGridData(2));
		repoConfigStackLayout = new StackLayout();
		repoConfigComposite.setLayout(repoConfigStackLayout);

		TabItem repoTabItem = new TabItem(tabFolder, SWT.FILL);
		repoTabItem.setControl(repoTab);
		repoTabItem.setText(UIText.GlobalConfigurationPreferencePage_repositorySettingTabTitle);

		if (repoCombo.getItemCount() > 0) {
			repoCombo.select(0);
			showRepositoryConfiguration(0);
		} else {
			repoCombo.setItems(new String[] {UIText.GlobalConfigurationPreferencePage_repositorySettingNoRepositories});
			repoCombo.select(0);
			repoCombo.setEnabled(false);
		}

		return composite;
