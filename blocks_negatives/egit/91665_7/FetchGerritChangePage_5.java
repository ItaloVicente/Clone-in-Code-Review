			return true;
		}
	}

	private void internalDoFetch(RefSpec spec, String uri, boolean doCheckout,
			boolean doCreateTag, boolean doChangeBranch,
			boolean doCreateBranch, boolean doCheckoutNewBranch,
			boolean doActivateAdditionalRefs, String textForTag, String textForBranch, IProgressMonitor monitor)
			throws IOException, CoreException, URISyntaxException {

		int totalWork = 1;
		if (doCheckout)
			totalWork++;
		if (doCreateTag || doCreateBranch)
			totalWork++;
		monitor.beginTask(
				UIText.FetchGerritChangePage_GetChangeTaskName,
				totalWork);

		if (doChangeBranch) {
			Ref localRef = getLocalRef(spec.getSource());
			checkout(localRef.getName(), monitor);
		} else {
			try {
				RevCommit commit = fetchChange(uri, spec, monitor);
