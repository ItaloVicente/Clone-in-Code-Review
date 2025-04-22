					tw.enterSubtree();
				} else {
					add(T_BASE, DirCacheEntry.STAGE_1);
					add(T_OURS, DirCacheEntry.STAGE_2);
					add(T_THEIRS, DirCacheEntry.STAGE_3);
					hasConflict = true;
