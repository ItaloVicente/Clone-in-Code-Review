			SEARCH: for (;;) {
				readFully(pos
				int c = ib[0] & 0xff;
				final int type = (c >> 4) & 7;
				long sz = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = ib[p++] & 0xff;
					sz += (c & 0x7f) << shift;
					shift += 7;
				}

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
							ldr = new LargePackedWholeObject(type
							break SEARCH;
						}
						decompress(pos + p
						ldr = new ObjectLoader.SmallObject(type
						break SEARCH;
