		builder.add(dce);
		String pathStr = new String(path, StandardCharsets.UTF_8);
		DirCacheEntry oldEntry = builder.getDirCache().getEntry(pathStr);

		if (oldEntry != null && !oldEntry.getObjectId().equals(objectId))
       markAsModified(pathStr);
