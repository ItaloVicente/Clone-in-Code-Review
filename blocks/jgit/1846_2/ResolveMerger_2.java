			if (nonTree(modeT)) {
				DirCacheEntry e = add(tw.getRawPath()
						DirCacheEntry.STAGE_0);
				if (e != null)
					toBeCheckedOut.put(tw.getPathString()
				return true;
			} else if (modeT == 0) {
				toBeCheckedOut.put(tw.getPathString()
				return true;
			}
