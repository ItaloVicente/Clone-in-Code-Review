		Repository repository = SelectionUtils.getRepository(selection);
		if (repository == null || repository.isBare()) {
			return false;
		}
		IndexDiffCacheEntry diffCacheEntry = Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repository);
		if (diffCacheEntry == null) {
			return false;
		}
		IndexDiffData indexDiffData = diffCacheEntry.getIndexDiff();
		if (indexDiffData == null) {
			return false;
		}

		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			IResource resource = AdapterUtils.adapt(iterator.next(),
					IResource.class);
