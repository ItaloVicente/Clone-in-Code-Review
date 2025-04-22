
		new Label(main, SWT.NONE)
				.setText(UIText.FetchGerritChangePage_RepositoryLabel);
		repositoryCombo = new Combo(main, SWT.DROP_DOWN);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(repositoryCombo);
		Repository[] repositories = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().getAllRepositories();

		for (Repository repo : repositories) {
			String name = Activator.getDefault().getRepositoryUtil()
					.getRepositoryName(repo);
			repositoryCombo.add(name);
			repositoryCombo.setData(name, repo);
		}

		repositoryCombo.setText(Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository));
		repositoryCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Repository selected = (Repository) repositoryCombo
						.getData(repositoryCombo.getText());
				FetchGerritChangePage.this.repository = selected;


			}
		});

