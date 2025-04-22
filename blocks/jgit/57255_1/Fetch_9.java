		try (Git git = new Git(db)) {
			FetchCommand fetch = git.fetch();
			if (fsck != null)
				fetch.setCheckFetchedObjects(fsck.booleanValue());
			if (prune != null)
				fetch.setRemoveDeletedRefs(prune.booleanValue());
			if (toget != null)
				fetch.setRefSpecs(toget);
			if (tags != null) {
				fetch.setTagOpt(tags.booleanValue() ? TagOpt.FETCH_TAGS
						: TagOpt.NO_TAGS);
			}
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
