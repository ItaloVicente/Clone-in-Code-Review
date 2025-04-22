			switch (typeCode) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				type = typeCode;
				visit.data = inflateAndReturn(Source.FILE
				break;
			case Constants.OBJ_OFS_DELTA: {
