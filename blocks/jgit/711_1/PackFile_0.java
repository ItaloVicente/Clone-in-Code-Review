		readFully(objectOffset
		int headerSize = 0;
		while ((buf[headerSize++] & 0x80) != 0) {
		}

		switch ((buf[0] >> 4) & 7) {
		case Constants.OBJ_OFS_DELTA:
			while ((buf[headerSize++] & 0x80) != 0) {
			}
			break;
		case Constants.OBJ_REF_DELTA:
			if (idx.hasCRC32Support()) {
				readFully(objectOffset + 20
			}
			headerSize += 20;
			break;
		}

		final long dataOffset = objectOffset + headerSize;
		final int cnt = (int) (findEndOffset(objectOffset) - dataOffset);

