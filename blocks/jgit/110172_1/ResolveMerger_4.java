
				if (gitlinkConflict) {
					failingPaths.put(tw.getPathString()
					if (!ignoreConflicts) {
						unmergedPaths.add(tw.getPathString());
					}
				} else {
					unmergedPaths.add(tw.getPathString());
				}
				return false;
