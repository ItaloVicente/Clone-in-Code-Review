		if (nonTree(modeO) && nonTree(modeT) && tw.idEqual(T_OURS
			if (modeO == modeT) {
				add(tw.getRawPath()
				return true;
			} else {
				int newMode = mergeFileModes(modeB
				if (newMode != FileMode.MISSING.getBits()) {
					if (newMode == modeO)
						add(tw.getRawPath()
					else {
						if (isWorktreeDirty())
							return false;
						DirCacheEntry e = add(tw.getRawPath()
								DirCacheEntry.STAGE_0);
						toBeCheckedOut.put(tw.getPathString()
					}
					return true;
				} else {
					add(tw.getRawPath()
					add(tw.getRawPath()
					add(tw.getRawPath()
					unmergedPaths.add(tw.getPathString());
					mergeResults.put(
							tw.getPathString()
							new MergeResult<RawText>(Collections
									.<RawText> emptyList()));
				}
				return true;
			}
