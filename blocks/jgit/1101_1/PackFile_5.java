	long getObjectSize(final WindowCursor curs
			throws IOException {
		final long offset = idx().findOffset(id);
		return 0 < offset ? getObjectSize(curs
	}

	long getObjectSize(final WindowCursor curs
			throws IOException {
		final byte[] ib = curs.tempId;
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

		long deltaAt;
		switch (type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			return sz;

		case Constants.OBJ_OFS_DELTA:
			c = ib[p++] & 0xff;
			while ((c & 128) != 0)
				c = ib[p++] & 0xff;
			deltaAt = pos + p;
			break;

		case Constants.OBJ_REF_DELTA:
			deltaAt = pos + p + 20;
			break;

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}

		try {
			return BinaryDelta.getResultSize(getDeltaHeader(curs
		} catch (DataFormatException e) {
			throw new CorruptObjectException(MessageFormat.format(JGitText
					.get().objectAtHasBadZlibStream
		}
	}

