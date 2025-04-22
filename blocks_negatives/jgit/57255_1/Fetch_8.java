		if (0 <= timeout)
			fetch.setTimeout(timeout);
		fetch.setDryRun(dryRun);
		fetch.setRemote(remote);
		if (thin != null)
			fetch.setThin(thin.booleanValue());
		if (quiet == null || !quiet.booleanValue())
			fetch.setProgressMonitor(new TextProgressMonitor(errw));

		FetchResult result = fetch.call();
		if (result.getTrackingRefUpdates().isEmpty())
			return;

		showFetchResult(result);
