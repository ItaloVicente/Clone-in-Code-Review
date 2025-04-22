			PackIndex idx = idx(ctx);
			DfsStreamKey revKey = new DfsStreamKey.ForReverseIndex(
					desc.getStreamKey(INDEX));
			revref = cache.getOrLoadRef(revKey,
					() -> loadReverseIdx(ctx, revKey, idx));
			PackReverseIndex revidx = revref.get();
			if (revidx != null) {
				reverseIndex = revref;
