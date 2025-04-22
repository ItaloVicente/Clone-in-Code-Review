	private static boolean hasAnyPathChanged(Collection<String> paths,
			IndexDiffData indexDiff) {
		for (String path : paths) {
			boolean hasChanged = indexDiff.getAdded().contains(path)
					|| indexDiff.getChanged().contains(path)
					|| indexDiff.getModified().contains(path)
					|| indexDiff.getRemoved().contains(path)
					|| indexDiff.getUntracked().contains(path);
			if (hasChanged)
				return true;
