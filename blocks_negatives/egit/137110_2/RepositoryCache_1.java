		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		if (cache != null) {
			for (File f : toRemove) {
				cache.remove(f);
			}
		}
