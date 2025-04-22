		Repository repository = getRepository();

		if (repository != null) {
			try {
				fillValues(repository);
			} catch (IOException e) {
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
			}
		}

		changeIdButton = new Button(composite, SWT.CHECK);
		changeIdButton.setText(UIText.CommitDialog_AddChangeIdLabel);
		changeIdButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		changeIdButton.setToolTipText(UIText.CommitDialog_AddChangeIdTooltip);
		if (repository != null) {
			StoredConfig config = repository.getConfig();
			boolean addChangeId = config.getBoolean(GERRIT, ADD_CHANGE_ID, false);
			changeIdButton.setSelection(addChangeId);
		}

		return composite;
	}

	@Override
	public boolean performOk() {
		Repository repository = getRepository();
		if (repository != null) {
			StoredConfig config = repository.getConfig();
			boolean addChangeId = changeIdButton.getSelection();
			if (addChangeId) {
				config.setBoolean(GERRIT, null, ADD_CHANGE_ID, true);
			} else {
				config.unset(GERRIT, null, ADD_CHANGE_ID);
				if (config.getNames(GERRIT).isEmpty()) {
					config.unsetSection(GERRIT, null);
				}
			}
			try {
				config.save();
			} catch (IOException e) {
				if (GitTraceLocation.UI.isActive()) {
					GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
				}
			}
		}
		return true;
	}

	private Repository getRepository() {
