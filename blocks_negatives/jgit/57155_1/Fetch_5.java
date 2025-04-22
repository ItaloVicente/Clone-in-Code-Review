		Git git = new Git(db);
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
