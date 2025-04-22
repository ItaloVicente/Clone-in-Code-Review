			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG: {
				if (sz < curs.getStreamFileThreshold()) {
					byte[] data;
