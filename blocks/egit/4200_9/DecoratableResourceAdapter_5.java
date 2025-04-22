			if (repository == null)
				return;
			indexDiffData = Activator.getDefault().getIndexDiffCache()
					.getIndexDiffCacheEntry(repository).getIndexDiff();
			if (indexDiffData == null)
				return;
