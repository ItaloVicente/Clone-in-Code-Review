
				if (need.isEmpty() && !shortCircuit.isEmpty()) {
					cachedPacks.addAll(shortCircuit);
					for (CachedPack pack : shortCircuit)
						countingMonitor.update((int) pack.getObjectCount());
					countingMonitor.endTask();
					stats.timeCounting = System.currentTimeMillis() - countingStart;
					return;
				}

