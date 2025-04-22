		FileInputStream inputStream = new FileInputStream(file);
		ObjectId id = newObjectInserter.insert(
				Constants.OBJ_BLOB, file.length(), inputStream);
		inputStream.close();
		DirCacheEntry entry = new DirCacheEntry(path, stage);
		entry.setObjectId(id);
		entry.setFileMode(FileMode.REGULAR_FILE);
		entry.setLastModified(file.lastModified());
		entry.setLength((int) file.length());

		builder.add(entry);
		return entry;
