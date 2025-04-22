		repoInfo.setLayout(layout);

		gitDir = createLabeledReadOnlyText(repoInfo, UIText.GitProjectPropertyPage_LabelGitDir);
		workDir = createLabeledReadOnlyText(repoInfo, UIText.GitProjectPropertyPage_LabelWorkdir);
		branch = createLabeledReadOnlyText(repoInfo, UIText.GitProjectPropertyPage_LabelBranch);
		id = createLabeledReadOnlyText(repoInfo, UIText.GitProjectPropertyPage_LabelId);
		state = createLabeledReadOnlyText(repoInfo, UIText.GitProjectPropertyPage_LabelState);

		Repository repository = getRepository();

		if (repository != null) {
			try {
				fillValues(repository);
			} catch (IOException e) {
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
			}
		}

		Group repoConfigGroup = new Group(composite, SWT.NONE);
		repoConfigGroup.setText(UIText.GitProjectPropertyPage_RepositoryConfigurationGroup);
		repoConfigGroup.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		repoConfigGroup.setLayout(new GridLayout());

		changeIdButton = new Button(repoConfigGroup, SWT.CHECK);
		changeIdButton.setText(UIText.GitProjectPropertyPage_AddChangeIdLabel);
		changeIdButton.setToolTipText(UIText.GitProjectPropertyPage_AddChangeIdTooltip);
		if (repository != null) {
			StoredConfig config = repository.getConfig();
			boolean addChangeId = config.getBoolean(GERRIT, ADD_CHANGE_ID, false);
			changeIdButton.setSelection(addChangeId);
		}

		return composite;
	}
