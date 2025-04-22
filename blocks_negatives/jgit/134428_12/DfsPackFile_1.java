			idxref = cache.getRef(idxKey);
			if (idxref != null) {
				PackIndex idx = idxref.get();
				if (idx != null) {
					index = idxref;
					return idx;
				}
			}

			PackIndex idx;
