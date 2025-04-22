	private static long mostRecentGC(DfsPackFile[] packs) {
		long r = 0;
		for (DfsPackFile p : packs) {
			DfsPackDescription d = p.getPackDescription();
			if (d.getPackSource() == GC || d.getPackSource() == GC_REST) {
				r = Math.max(r, d.getLastModified());
			}
		}
		return r;
	}

	private boolean packIsExpiredGarbage(DfsPackDescription d,
			long mostRecentGC, long now) {
