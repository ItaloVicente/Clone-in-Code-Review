		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.STAGING_VIEW_FILENAME_MODE, false);

		SWTBotView view = TestUtil.showView(StagingView.VIEW_ID);
		TestUtil.joinJobs(org.eclipse.egit.core.JobFamilies.INDEX_DIFF_CACHE_UPDATE);

