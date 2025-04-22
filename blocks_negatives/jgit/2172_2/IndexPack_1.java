		switch (typeCode) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			type = typeCode;
			data = inflateAndReturn(Source.FILE, sz);
			break;
		case Constants.OBJ_OFS_DELTA: {
			c = readFrom(Source.FILE) & 0xff;
			while ((c & 128) != 0)
