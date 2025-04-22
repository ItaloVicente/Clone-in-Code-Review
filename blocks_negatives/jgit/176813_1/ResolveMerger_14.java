			if (nonTree(modeO) && !nonTree(modeT)) {
				if (nonTree(modeB))
					add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
				add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
				unmergedPaths.add(tw.getPathString());
				enterSubtree = false;
				return true;
			}
			if (nonTree(modeT) && !nonTree(modeO)) {
