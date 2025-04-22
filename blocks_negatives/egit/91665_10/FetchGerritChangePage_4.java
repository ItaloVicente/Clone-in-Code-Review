			return true;
		}
	}

	private void internalDoFetch(RefSpec spec, String uri, boolean doCheckout,
			boolean doCreateTag, boolean doCreateBranch,
			boolean doCheckoutNewBranch, boolean doActivateAdditionalRefs,
			String textForTag, String textForBranch, IProgressMonitor monitor)
			throws IOException, CoreException, URISyntaxException {

		int totalWork = 1;
		if (doCheckout)
			totalWork++;
		if (doCreateTag || doCreateBranch)
			totalWork++;
		monitor.beginTask(
				UIText.FetchGerritChangePage_GetChangeTaskName,
				totalWork);

		try {
			RevCommit commit = fetchChange(uri, spec, monitor);

			if (doCreateTag)
				createTag(spec, textForTag, commit, monitor);

			if (doCreateBranch)
				createBranch(textForBranch, doCheckoutNewBranch, commit,
						monitor);
