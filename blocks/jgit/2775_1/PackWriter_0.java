		if (!edgeObjects.isEmpty() || !cachedPacks.isEmpty()) {
			for (Statistics.ObjectType typeStat : stats.objectTypes) {
				if (typeStat == null)
					continue;
				stats.thinPackBytes += typeStat.bytes;
			}
		}
