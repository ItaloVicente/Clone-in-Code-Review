	@Override
	protected void initialize() {
		super.initialize();
		preferredMergeStrategyEditor
				.setPreferenceStore(getCorePreferenceStore());
		preferredMergeStrategyEditor.load();
	}

