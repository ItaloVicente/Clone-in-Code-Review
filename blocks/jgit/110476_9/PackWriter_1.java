	private void filterAndAddObject(@NonNull AnyObjectId src
			int pathHashCode) throws IOException {
		boolean reject = filterBlobLimit >= 0 &&
			type == OBJ_BLOB &&
			reader.getObjectSize(src
		if (!reject) {
			addObject(src
		}
	}

