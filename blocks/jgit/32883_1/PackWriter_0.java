			writeObjects(out);
			if (!edgeObjects.isEmpty() || !cachedPacks.isEmpty()) {
				for (Statistics.ObjectType typeStat : stats.objectTypes) {
					if (typeStat == null)
						continue;
					stats.thinPackBytes += typeStat.bytes;
				}
			}

			stats.reusedPacks = Collections.unmodifiableList(cachedPacks);
			for (CachedPack pack : cachedPacks) {
				long deltaCnt = pack.getDeltaCount();
				stats.reusedObjects += pack.getObjectCount();
				stats.reusedDeltas += deltaCnt;
				stats.totalDeltas += deltaCnt;
				reuseSupport.copyPackAsIs(out
			}
			writeChecksum(out);
			out.flush();
		} finally {
			stats.timeWriting = System.currentTimeMillis() - writeStart;
			stats.depth = depth;
