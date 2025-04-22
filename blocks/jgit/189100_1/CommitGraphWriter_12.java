	private void computeChangedPaths(ProgressMonitor computeBloomFilterMonitor
			List<RevCommit> commits) throws IOException {
		bloomFilterComputedCount = 0;
		int newFiltersLimit = maxNewFilters > 0 ? maxNewFilters
				: commits.size();

		beginPhase(JGitText.get().computingCommitChangedPathsBloomFilters
				computeBloomFilterMonitor

		totalBloomFilterDataSize = 0;
		DiffFormatter formatter = new DiffFormatter(NullOutputStream.INSTANCE);
		formatter.setReader(walk.getObjectReader()
		formatter.setDetectRenames(false);
		formatter.setMaxDiffEntryScan(BLOOM_MAX_CHANGED_PATHS + 1);

		for (RevCommit c : commits) {
			ChangedPathFilter filter = getOrComputeBloomFilter(formatter
					BLOOM_MAX_CHANGED_PATHS
					bloomFilterComputedCount < newFiltersLimit);
			if (filter != null) {
				totalBloomFilterDataSize += filter.getSize();
				setBloomFilter(c
			}
			computeBloomFilterMonitor.update(1);
		}
		endPhase(computeBloomFilterMonitor);
	}

	private ChangedPathFilter getOrComputeBloomFilter(DiffFormatter formatter
			RevCommit c
			boolean computeIfNotPresent) throws IOException {
		if (oldGraph != null) {
			BloomFilter filter = oldGraph.findBloomFilter(c);
			if (filter != null && filter instanceof ChangedPathFilter) {
				return (ChangedPathFilter) filter;
			}
		}

		if (!computeIfNotPresent) {
			return null;
		}

		List<DiffEntry> diffs;

		if (c.getParentCount() > 0) {
			diffs = formatter.scan(c.getParent(0).getTree()
		} else {
			diffs = formatter.scan(null
		}
		bloomFilterComputedCount++;

		if (diffs.size() > maxChangedPaths) {
			return TRUNCATED_LARGE_FILTER;
		}

		Set<String> pathSet = new HashSet<>();
		for (DiffEntry diff : diffs) {
			String path;
			if (diff.getChangeType().equals(DiffEntry.ChangeType.DELETE)) {
				path = diff.getOldPath();
			} else {
				path = diff.getNewPath();
			}

			if (path != null) {
				do {
					pathSet.add(path);

					int pos = path.lastIndexOf('/');
					if (pos > 0) {
						path = path.substring(0
					} else {
						path = null;
					}
				} while (!StringUtils.isEmptyOrNull(path));
			}
		}

		if (pathSet.size() > maxChangedPaths) {
			return TRUNCATED_LARGE_FILTER;
		}

		int filterLen = (pathSet.size() * bitsPerEntry + Byte.SIZE - 1)
				/ Byte.SIZE;

		if (filterLen <= 0) {
			return TRUNCATED_EMPTY_FILTER;
		}

		ChangedPathFilter filter = new ChangedPathFilter(filterLen

		for (String path : pathSet) {
			BloomFilter.Key key = ChangedPathFilter.newBloomKey(path
					numHashes);
			filter.addKey(key);
		}

		return filter;
	}

