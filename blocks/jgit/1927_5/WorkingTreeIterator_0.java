
		if (state.walk != null) {
			DirCacheIterator i = state.walk.getTree(state.dirCacheTree
					DirCacheIterator.class);
			if (i != null) {
				DirCacheEntry ent = i.getDirCacheEntry();
				if (ent != null
						&& compareMetadata(ent) == MetadataDiff.DIFFER_BY_TIMESTAMP)
					return i.idBuffer();
			}
		}

