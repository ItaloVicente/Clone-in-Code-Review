	public boolean isIgnoredInOldIndex(IndexDiffCacheEntry entry, String path) {
		if (entry == null)
			return false;

		IndexDiffData indexDiff = entry.getIndexDiff();
		if (indexDiff == null)
			return false;

		for (String s : indexDiff.getIgnoredNotInIndex()) {
			String sWithSlash = s;
			if (!s.endsWith("/")) { //$NON-NLS-1$
				sWithSlash = s + '/';
			}

			if (path.equals(s) || path.startsWith(sWithSlash)) {
				return true;
			}
		}
		return false;
	}

