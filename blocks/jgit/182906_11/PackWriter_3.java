				writeObjects(out);
				if (!edgeObjects.isEmpty() || !cachedPacks.isEmpty()) {
					for (PackStatistics.ObjectType.Accumulator typeStat : stats.objectTypes) {
						if (typeStat == null)
							continue;
						stats.thinPackBytes += typeStat.bytes;
