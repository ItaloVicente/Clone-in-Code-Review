
			stats.reusedPacks = Collections.unmodifiableList(cachedPacks);
			for (CachedPack pack : unwrittenCachedPacks) {
				long deltaCnt = pack.getDeltaCount();
				stats.reusedObjects += pack.getObjectCount();
				stats.reusedDeltas += deltaCnt;
				stats.totalDeltas += deltaCnt;
				reuseSupport.copyPackAsIs(out, pack);
			}
			writeChecksum(out);
			out.flush();
		} finally {
			stats.timeWriting = System.currentTimeMillis() - writeStart;
			stats.depth = depth;

			for (PackStatistics.ObjectType.Accumulator typeStat : stats.objectTypes) {
				if (typeStat == null)
					continue;
				typeStat.cntDeltas += typeStat.reusedDeltas;
				stats.reusedObjects += typeStat.reusedObjects;
				stats.reusedDeltas += typeStat.reusedDeltas;
				stats.totalDeltas += typeStat.cntDeltas;
			}
