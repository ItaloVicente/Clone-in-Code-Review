			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.FETCH.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.schedule();
		return true;
	}

	void internalDoFetch(RefSpec spec, String uri, boolean doCheckout,
			boolean doCreateTag, boolean doCreateBranch,
			boolean doActivateAdditionalRefs, String textForTag,
			String textForBranch, IProgressMonitor monitor) {

		int totalWork = 1;
		if (doCheckout)
			totalWork++;
		if (doCreateTag || doCreateBranch)
			totalWork++;
		monitor.beginTask(
				UIText.FetchGerritChangePage_GetChangeTaskName,
				totalWork);

		try {
			RevCommit commit = fetchChange(uri, spec,
					monitor);

			if (doCreateTag) {
				createTag(spec, textForTag, commit, monitor);
			}
			if (doCreateBranch) {
				createBranch(textForBranch, commit, monitor);
			}
			if (doCheckout || doCreateTag) {
				checkout(commit, monitor);
			}
			if (doActivateAdditionalRefs) {
				activateAdditionalRefs();
			}
			storeLastUsedUri(uri);
		} catch (Exception e) {
