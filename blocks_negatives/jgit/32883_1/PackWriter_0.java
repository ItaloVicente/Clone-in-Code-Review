		for (CachedPack pack : cachedPacks) {
			long deltaCnt = pack.getDeltaCount();
			stats.reusedObjects += pack.getObjectCount();
			stats.reusedDeltas += deltaCnt;
			stats.totalDeltas += deltaCnt;
			reuseSupport.copyPackAsIs(out, pack, reuseValidate);
		}
		writeChecksum(out);
		out.flush();
		stats.timeWriting = System.currentTimeMillis() - writeStart;
