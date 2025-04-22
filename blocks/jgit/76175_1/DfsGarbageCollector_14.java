		return r;
	}

	private boolean packIsExpiredGarbage(DfsPackDescription d
			long mostRecentGC
		return d.getPackSource() == UNREACHABLE_GARBAGE
				&& d.getLastModified() < mostRecentGC
				&& garbageTtlMillis > 0
				&& now - d.getLastModified() >= garbageTtlMillis;
