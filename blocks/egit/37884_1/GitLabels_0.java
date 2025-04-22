
		IndexDiffData indexDiffData = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repository).getIndexDiff();

		if (indexDiffData != null
				&& (!indexDiffData.getAdded().isEmpty()
				|| !indexDiffData.getChanged().isEmpty()
				|| !indexDiffData.getRemoved().isEmpty()
						|| !indexDiffData.getUntracked().isEmpty()
						|| !indexDiffData.getModified().isEmpty() || !indexDiffData
						.getMissing().isEmpty())) {
			string.append('>');
			string.append(' ');
		}

