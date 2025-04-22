	private boolean isIgnoredInOldIndex(IndexDiffCacheEntry entry, String path) {
		if (entry == null || gitIgnoreChanged)
			return false;

		IndexDiffData indexDiff = entry.getIndexDiff();
		if (indexDiff == null)
			return false;

		String p = path;
		Set<String> ignored = indexDiff.getIgnoredNotInIndex();
		while (p != null) {
			if (ignored.contains(p))
				return true;

			p = skipLastSegment(p);
		}

		return false;
	}

	private String skipLastSegment(String path) {
		if (path.indexOf('/') == -1) {
			return null;
		}

		return path.substring(0, path.lastIndexOf('/'));
	}

