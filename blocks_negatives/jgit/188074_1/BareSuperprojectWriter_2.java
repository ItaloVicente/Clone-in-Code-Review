		String content = cfg.toText();

		DirCacheEntry dcEntry = new DirCacheEntry(
				Constants.DOT_GIT_MODULES);
		ObjectId objectId = inserter.insert(Constants.OBJ_BLOB,
				content.getBytes(UTF_8));
		dcEntry.setObjectId(objectId);
		dcEntry.setFileMode(FileMode.REGULAR_FILE);
		builder.add(dcEntry);
