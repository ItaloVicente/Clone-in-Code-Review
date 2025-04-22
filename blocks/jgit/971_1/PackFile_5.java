	LocalObjectRepresentation representation(final WindowCursor curs
			final AnyObjectId objectId) throws IOException {
		final long pos = idx().findOffset(objectId);
		if (pos < 0)
			return null;

		final byte[] ib = curs.tempId;
		readFully(pos
		int c = ib[0] & 0xff;
		int p = 1;
		final int typeCode = (c >> 4) & 7;
		while ((c & 0x80) != 0)
			c = ib[p++] & 0xff;

		long len = (findEndOffset(pos) - pos);
		switch (typeCode) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			return LocalObjectRepresentation.newWhole(this

		case Constants.OBJ_OFS_DELTA: {
			c = ib[p++] & 0xff;
			long ofs = c & 127;
			while ((c & 128) != 0) {
				ofs += 1;
				c = ib[p++] & 0xff;
				ofs <<= 7;
				ofs += (c & 127);
			}
			ofs = pos - ofs;
			return LocalObjectRepresentation.newDelta(this
		}

		case Constants.OBJ_REF_DELTA: {
			len -= p;
			len -= Constants.OBJECT_ID_LENGTH;
			readFully(pos + p
			ObjectId id = ObjectId.fromRaw(ib);
			return LocalObjectRepresentation.newDelta(this
		}

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
	}

