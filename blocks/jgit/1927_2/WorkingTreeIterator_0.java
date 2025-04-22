
		if (state.walk != null) {
			DirCacheIterator i = state.walk.getTree(state.dirCacheTree
					DirCacheIterator.class);
			if (i != null) {
				DirCacheEntry ent = i.getDirCacheEntry();
				if (ent != null && !isModified(ent
						FS.DETECTED)) {
					return i.idBuffer();
				}
			}
		}

