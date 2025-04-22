			idxref = index;
			if (idxref != null) {
				PackIndex idx = idxref.get();
				if (idx != null)
					return idx;
			}

			DfsStreamKey idxKey = desc.getStreamKey(INDEX);
			idxref = cache.getRef(idxKey);
			if (idxref != null) {
				PackIndex idx = idxref.get();
				if (idx != null) {
					index = idxref;
					return idx;
				}
