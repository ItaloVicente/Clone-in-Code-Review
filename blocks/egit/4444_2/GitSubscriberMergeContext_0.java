		IndexDiffCache indexDiffCache = Activator.getDefault()
				.getIndexDiffCache();
		if (indexDiffCache != null)
			indexDiffCache
					.removeIndexDiffChangedListener(indexDiffChangeListener);

