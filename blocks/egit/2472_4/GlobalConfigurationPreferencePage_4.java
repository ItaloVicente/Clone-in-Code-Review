
		Composite repoTab = new Composite(tabFolder, SWT.NONE);
		repoTab.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(repoTab);
		Composite repositoryComposite = new Composite(repoTab, SWT.NONE);
		repositoryComposite.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(
				repositoryComposite);
		Label repoLabel = new Label(repositoryComposite, SWT.NONE);
		repoLabel
				.setText(UIText.GlobalConfigurationPreferencePage_repositorySettingRepositoryLabel);

		Combo repoCombo = new Combo(repositoryComposite, SWT.READ_ONLY);
		repoCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.widget;
				showRepositoryConfiguration(combo.getSelectionIndex());
			}
		});
		repoCombo.setItems(getRepositoryComboItems());

		repoConfigComposite = new Composite(repoTab, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(repoConfigComposite);
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
