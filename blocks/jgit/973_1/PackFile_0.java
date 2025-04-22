		try {
			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG: {
				byte[] data = decompress(pos + p
				return new PackedObjectLoader(type
			}
