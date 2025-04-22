		try (InputStream inputStream = Files.newInputStream(file.toPath())) {
			ObjectId id = newObjectInserter.insert(Constants.OBJ_BLOB
					file.length()
			DirCacheEntry entry = new DirCacheEntry(path
			entry.setObjectId(id);
			entry.setFileMode(FileMode.REGULAR_FILE);
			entry.setLastModified(file.lastModified());
			entry.setLength((int) file.length());

			builder.add(entry);
			return entry;
		}
