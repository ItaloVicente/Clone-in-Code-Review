	private List<ObjectIdWithOffset> toInclude(DfsPackFile src
			throws IOException {
		PackIndex srcIdx = src.getPackIndex(ctx);
		List<ObjectIdWithOffset> want = new BlockList<ObjectIdWithOffset>(
				(int) srcIdx.getObjectCount());
		SCAN: for (PackIndex.MutableEntry ent : srcIdx) {
			ObjectId id = ent.toObjectId();
			RevObject obj = rw.lookupOrNull(id);
			if (obj != null && (obj.has(added) || obj.has(isBase)))
				continue;
			for (PackWriter.ObjectIdSet e : exclude)
				if (e.contains(id))
					continue SCAN;
			want.add(new ObjectIdWithOffset(id
		}
		Collections.sort(want
			public int compare(ObjectIdWithOffset a
				return Long.signum(a.offset - b.offset);
			}
		});
		return want;
	}

