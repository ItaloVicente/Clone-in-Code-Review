	private void filterAndAddObject(@NonNull AnyObjectId src
			int pathHashCode) throws IOException {
		boolean reject = blobMaxBytes >= 0 &&
			type == OBJ_BLOB &&
			reader.getObjectSize(src
		if (!reject) {
			addObject(src
		}
	}

