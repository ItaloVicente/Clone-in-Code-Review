
		Composite repoTab = SWTUtils.createHVFillComposite(tabFolder, SWTUtils.MARGINS_NONE);
		Combo repoCombo = new Combo(repoTab, SWT.READ_ONLY);
		repoCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.widget;
				showRepositoryConfiguration(combo.getSelectionIndex());
			}
		});
		repoCombo.setItems(getRepositoryComboItems());

		repoConfigComposite = SWTUtils.createHVFillComposite(repoTab, SWT.NONE);
		repoConfigComposite.setLayoutData(SWTUtils.createHVFillGridData());
		repoConfigStackLayout = new StackLayout();
		repoConfigComposite.setLayout(repoConfigStackLayout);

		TabItem repoTabItem = new TabItem(tabFolder, SWT.FILL);
		repoTabItem.setControl(repoTab);
		repoTabItem.setText(UIText.GlobalConfigurationPreferencePage_repositorySettingTabTitle);

		if (repoCombo.getItemCount() > 0) {
			repoCombo.select(0);
			showRepositoryConfiguration(0);
		}

		return composite;
	}

	private String[] getRepositoryComboItems() {
		List<String> items = new ArrayList<String>();
		for (Repository repository : repositories) {
			String repoName = repository.getDirectory().getParentFile().getName();
			items.add(repoName);
		}
		return items.toArray(new String[items.size()]);
	}

	private void showRepositoryConfiguration(int index) {
		Repository repository = repositories.get(index);
		ConfigurationEditorComponent editor = repoConfigEditors.get(repository);
		if (editor == null) {
			editor = createConfigurationEditor(repository);
			repoConfigEditors.put(repository, editor);
		}
		repoConfigStackLayout.topControl = editor.getContents();
		repoConfigComposite.layout();
	}

	private ConfigurationEditorComponent createConfigurationEditor(final Repository repository) {
		ConfigurationEditorComponent editorComponent = new ConfigurationEditorComponent(repoConfigComposite, repository.getConfig(), true) {
			@Override
			protected void setErrorMessage(String message) {
				GlobalConfigurationPreferencePage.this.setErrorMessage(message);
			}

			@Override
			protected void setDirty(boolean dirty) {
				if (dirty)
					dirtyRepositories.add(repository);
				else
					dirtyRepositories.remove(repository);
				updateApplyButton();
			}
		};
		editorComponent.createContents();
		return editorComponent;
