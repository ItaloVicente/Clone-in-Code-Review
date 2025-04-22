		final Object[] array = repositories.keySet().toArray();
		treeViewer.setInput(array);
		treeViewer.setCheckedElements(array);
		repositoriesColumn.getColumn().pack();

		save();

		treeViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				selectedRepositories.clear();
				selectedProjects.clear();

				save();
				validatePage();
			}
		});

		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		layout = new GridLayout(2, true);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		buttonsComposite.setLayout(layout);
		buttonsComposite.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));

		Button selectAllBtn = new Button(buttonsComposite, SWT.PUSH);
		selectAllBtn.setText(UIText.GitBranchSynchronizeWizardPage_selectAll);
		selectAllBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				treeViewer.setCheckedElements(array);
				save();
				validatePage();
			}
		});
		selectAllBtn.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false,
				false));

		Button deselectAllBtn = new Button(buttonsComposite, SWT.PUSH);
		deselectAllBtn.setText(UIText.GitBranchSynchronizeWizardPage_deselectAll);
		deselectAllBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				treeViewer.setCheckedElements(new Object[0]);
				selectedRepositories.clear();
				selectedProjects.clear();
				validatePage();
			}
		});
		deselectAllBtn.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				false, false));
