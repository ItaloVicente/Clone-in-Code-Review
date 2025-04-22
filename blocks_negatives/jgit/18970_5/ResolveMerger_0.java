			}

			if (isGitLink(modeO) || isGitLink(modeT)) {
				add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, 0, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, 0, 0);
				add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, 0, 0);
