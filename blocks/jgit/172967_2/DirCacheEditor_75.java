				if (lastIdx > eIdx + 1) {
					DirCacheEntry[] tmp = new DirCacheEntry[lastIdx - eIdx];
					int n = 0;
					for (int i = eIdx; i < lastIdx; i++) {
						DirCacheEntry ent = cache.getEntry(i);
						e.apply(ent);
						if (ent.getStage() == DirCacheEntry.STAGE_0) {
							fastAdd(ent);
							n = 0;
							break;
						}
						tmp[n++] = ent;
					}
					for (int i = 0; i < n; i++) {
						fastAdd(tmp[i]);
					}
				} else {
					DirCacheEntry ent = cache.getEntry(eIdx);
