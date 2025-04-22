			if (c.has(inCachedPack)) {
				CachedPack pack = tipToPack.get(c);
				if (includesAllTips(pack, include, walker)) {
					useCachedPack(walker, keepOnRestart, //
							wantObjs, haveObjs, pack);
					commits = new BlockList<RevCommit>();

					endPhase(countingMonitor);
					beginPhase(PackingPhase.COUNTING, countingMonitor,
							ProgressMonitor.UNKNOWN);
					continue;
				}
			}

