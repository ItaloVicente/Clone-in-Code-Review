		final Transport tn = Transport.open(db, remote);
		if (fsck != null)
			tn.setCheckFetchedObjects(fsck.booleanValue());
		if (prune != null)
			tn.setRemoveDeletedRefs(prune.booleanValue());
		tn.setDryRun(dryRun);
		if (thin != null)
			tn.setFetchThin(thin.booleanValue());
		if (0 <= timeout)
			tn.setTimeout(timeout);
		final FetchResult r;
		try {
			r = tn.fetch(new TextProgressMonitor(), toget);
			if (r.getTrackingRefUpdates().isEmpty())
				return;
		} finally {
			tn.close();
		}
		showFetchResult(tn, r);
