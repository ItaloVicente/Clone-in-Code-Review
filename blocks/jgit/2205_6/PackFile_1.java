			final byte[] ib = curs.tempId;
			Delta delta = null;
			byte[] data = null;
			int type = Constants.OBJ_BAD;
			boolean cached = false;

			SEARCH: for (;;) {
				readFully(pos
				int c = ib[0] & 0xff;
				final int typeCode = (c >> 4) & 7;
				long sz = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = ib[p++] & 0xff;
					sz += (c & 0x7f) << shift;
					shift += 7;
				}

				switch (typeCode) {
				case Constants.OBJ_COMMIT:
				case Constants.OBJ_TREE:
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TAG: {
					if (sz < curs.getStreamFileThreshold())
						data = decompress(pos + p

					if (delta != null) {
						type = typeCode;
						break SEARCH;
