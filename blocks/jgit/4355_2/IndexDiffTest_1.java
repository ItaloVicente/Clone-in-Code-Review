
	static PathEdit add(final Repository db
			final String path) throws FileNotFoundException
		ObjectInserter inserter = db.newObjectInserter();
		final File f = new File(workdir
		final ObjectId id = inserter.insert(Constants.OBJ_BLOB
				IO.readFully(f));
		return new PathEdit(path) {
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setLength(f.length());
				ent.setObjectId(id);
			}
		};
	}

