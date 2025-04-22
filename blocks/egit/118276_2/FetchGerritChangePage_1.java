
		new Label(main, SWT.NONE)
				.setText(UIText.FetchGerritChangePage_RepositoryLabel);
		repositoryCombo = new Combo(main, SWT.DROP_DOWN);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(repositoryCombo);
		Repository[] repositories = repositoryCache.getAllRepositories();

		for (Repository repo : repositories) {
			String name = repositoryUtil
					.getRepositoryName(repo);
			repositoryCombo.add(name);
			repositoryCombo.setData(name, repo);
		}

		repositoryCombo.select(0);
		repositoryCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Repository selected = (Repository) repositoryCombo
						.getData(repositoryCombo.getText());
				FetchGerritChangePage.this.repository = selected;

			}
		});

