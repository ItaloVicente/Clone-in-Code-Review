		Group searchGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		searchGroup.setText(UIText.RepositorySearchDialog_SearchCriteriaGroup);
		searchGroup.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().span(4, 1).applyTo(searchGroup);

		Label dirLabel = new Label(searchGroup, SWT.NONE);
