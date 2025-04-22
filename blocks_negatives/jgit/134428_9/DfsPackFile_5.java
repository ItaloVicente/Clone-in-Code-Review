			DfsStreamKey revKey =
					new DfsStreamKey.ForReverseIndex(desc.getStreamKey(INDEX));
			revref = cache.getRef(revKey);
			if (revref != null) {
				PackReverseIndex idx = revref.get();
				if (idx != null) {
					reverseIndex = revref;
					return idx;
				}
