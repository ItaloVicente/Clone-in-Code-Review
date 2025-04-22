	boolean hasObject2(final String objectName) {
		return fileFor(objectName).exists();
	}

	ObjectLoader openObject2(final WindowCursor curs,
			final String objectName, final AnyObjectId objectId)
			throws IOException {
		try {
			File path = fileFor(objectName);
			FileInputStream in = new FileInputStream(path);
			try {
				unpackedObjectCache.add(objectId);
				return UnpackedObject.open(in, path, objectId, curs);
			} finally {
				in.close();
			}
		} catch (FileNotFoundException noFile) {
			unpackedObjectCache.remove(objectId);
			return null;
		}
	}

