			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG: {
				if (sz < curs.getStreamFileThreshold()) {
					byte[] data;
					try {
						data = new byte[(int) sz];
					} catch (OutOfMemoryError tooBig) {
						return largeWhole(curs, pos, type, sz, p);
