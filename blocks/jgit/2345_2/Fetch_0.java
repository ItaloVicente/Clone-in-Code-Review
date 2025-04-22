		if (fsck != null)
			fetch.setCheckFetchedObjects(fsck.booleanValue());
		if (prune != null)
			fetch.setRemoveDeletedRefs(prune.booleanValue());
		if (toget != null)
			fetch.setRefSpecs(toget);
		if (0 <= timeout)
			fetch.setTimeout(timeout);
