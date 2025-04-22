
		IndexDiffCacheEntry entry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repository);
		if (entry != null) {
			IndexDiffData indexDiffData = entry.getIndexDiff();
			if (indexDiffData != null
					&& (!indexDiffData.getAdded().isEmpty()
							|| !indexDiffData.getChanged().isEmpty()
							|| !indexDiffData.getRemoved().isEmpty()
							|| !indexDiffData.getUntracked().isEmpty()
							|| !indexDiffData.getModified().isEmpty()
							|| !indexDiffData.getMissing().isEmpty())) {
				string.append('>');
				string.append(' ');
			}
		}

