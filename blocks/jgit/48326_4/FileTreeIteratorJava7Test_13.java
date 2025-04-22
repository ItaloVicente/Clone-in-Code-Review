		try (ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId linkid = oi.insert(Constants.OBJ_BLOB
					NORMALIZED_BYTES
			DirCache dc = db.lockDirCache();
			DirCacheEditor dce = dc.editor();
			dce.add(new DirCacheEditor.PathEdit("link") {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(linkid);
					ent.setLength(NORMALIZED_BYTES.length);
				}
			});
			assertTrue(dce.commit());
		}
