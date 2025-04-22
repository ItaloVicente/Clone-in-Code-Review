		testRepository = new TestRepository<>(repository);
		mergeMode = org.eclipse.egit.ui.Activator.getDefault()
				.getPreferenceStore().getInt(UIPreferences.MERGE_MODE);
	}

	@After
	public void resetMergeMode() throws Exception {
		org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.MERGE_MODE, mergeMode);
