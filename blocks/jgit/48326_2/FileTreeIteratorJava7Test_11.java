		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					UNNORMALIZED_BYTES
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(UNNORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
