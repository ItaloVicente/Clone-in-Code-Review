			if (((modeO != 0 && !tw.idEqual(T_BASE, T_OURS)) || (modeT != 0 && !tw
					.idEqual(T_BASE, T_THEIRS)))) {

				add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, 0, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, 0, 0);
				DirCacheEntry e = add(tw.getRawPath(), theirs,
						DirCacheEntry.STAGE_3, 0, 0);
