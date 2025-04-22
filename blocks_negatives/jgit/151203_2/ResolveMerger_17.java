			} else {
				int newMode = mergeFileModes(modeB, modeO, modeT);
				if (newMode != FileMode.MISSING.getBits()) {
					if (newMode == modeO)
						keep(ourDce);
					else {
						if (isWorktreeDirty(work, ourDce))
							return false;
						DirCacheEntry e = add(tw.getRawPath(), theirs,
								DirCacheEntry.STAGE_0, EPOCH, 0);
						addToCheckout(tw.getPathString(), e, attributes);
					}
					return true;
