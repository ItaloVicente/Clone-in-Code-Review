	public DfsPackCompactor exclude(PackWriter.ObjectIdSet set) {
		exclude.add(set);
		return this;
	}

	public DfsPackCompactor exclude(DfsPackFile pack) throws IOException {
		final PackIndex idx;
		DfsReader ctx = (DfsReader) repo.newObjectReader();
		try {
			idx = pack.getPackIndex(ctx);
		} finally {
			ctx.release();
		}
		return exclude(new PackWriter.ObjectIdSet() {
			public boolean contains(AnyObjectId id) {
				return idx.hasObject(id);
			}
		});
	}

