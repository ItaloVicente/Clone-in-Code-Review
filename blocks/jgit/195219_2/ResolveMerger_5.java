			if (hasSymlink) {
				if (ignoreConflicts) {
					if (((modeT & FileMode.TYPE_MASK) == FileMode.TYPE_FILE)) {
						DirCacheEntry e = add(tw.getRawPath()
								DirCacheEntry.STAGE_0
						addToCheckout(currentPath
					} else {
						keep(ourDce);
					}
				} else {
					DirCacheEntry e = addConflict(base
					mergeResults.put(currentPath
					if (((modeT & FileMode.TYPE_MASK) == FileMode.TYPE_FILE)
							&& e != null) {
						addToCheckout(currentPath
					}
				}
			} else {
				updateIndex(base
			}
