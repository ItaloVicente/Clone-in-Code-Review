		for (Statistics.ObjectType typeStat : stats.objectTypes) {
			if (typeStat == null)
				continue;
			typeStat.cntDeltas += typeStat.reusedDeltas;

			stats.reusedObjects += typeStat.reusedObjects;
			stats.reusedDeltas += typeStat.reusedDeltas;
			stats.totalDeltas += typeStat.cntDeltas;
		}

