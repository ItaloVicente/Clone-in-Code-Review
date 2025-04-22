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
