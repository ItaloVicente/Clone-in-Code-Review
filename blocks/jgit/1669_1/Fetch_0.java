		Git git = new Git(db);
		FetchCommand fetch = git.fetch();
		fetch.setCheckFetchedObjects(fsck.booleanValue());
		fetch.setRemoveDeletedRefs(prune.booleanValue());
		fetch.setRefSpecs(toget);
		fetch.setTimeout(timeout);
		fetch.setProgressMonitor(new TextProgressMonitor());

		FetchResult result = fetch.call();
		if (result.getTrackingRefUpdates().isEmpty())
			return;

