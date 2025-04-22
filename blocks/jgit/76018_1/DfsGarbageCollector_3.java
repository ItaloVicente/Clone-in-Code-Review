		packsBefore = new ArrayList<DfsPackFile>(packs.length);
		expiredGarbagePacks = new ArrayList<DfsPackFile>(packs.length);

		long mostRecentGC = mostRecentGC(packs);
		long now = System.currentTimeMillis();
		for (DfsPackFile p : packs) {
			DfsPackDescription d = p.getPackDescription();
			if (d.getPackSource() != UNREACHABLE_GARBAGE) {
				packsBefore.add(p);
			} else if (packIsExpiredGarbage(d
				expiredGarbagePacks.add(p);
			} else if (d.getFileSize(PackExt.PACK) < coalesceGarbageLimit) {
				packsBefore.add(p);
			}
		}
	}

	private static long mostRecentGC(DfsPackFile[] packs) {
		long r = 0;
