			if (useCachedPacks && reuseSupport != null) {
				Set<ObjectId> need = new HashSet<ObjectId>(want);
				List<CachedPack> shortCircuit = new LinkedList<CachedPack>();

				for (CachedPack pack : reuseSupport.getCachedPacks()) {
					if (need.containsAll(pack.getTips())) {
						need.removeAll(pack.getTips());
						shortCircuit.add(pack);
					}

					for (ObjectId id : pack.getTips()) {
						tipToPack.put(id, pack);
						all.add(id);
					}
				}

				if (need.isEmpty() && !shortCircuit.isEmpty()) {
					cachedPacks.addAll(shortCircuit);
					for (CachedPack pack : shortCircuit)
						countingMonitor.update((int) pack.getObjectCount());
					endPhase(countingMonitor);
					stats.timeCounting = System.currentTimeMillis() - countingStart;
					return;
				}

				haveEst += tipToPack.size();
			}
