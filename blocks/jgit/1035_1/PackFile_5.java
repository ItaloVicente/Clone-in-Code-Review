	private byte[] getDeltaHeader(long pos
			throws IOException
		final byte[] hdr = new byte[18];
		wc.inflate(this
		return hdr;
	}

	private int getObjectType(final WindowCursor curs
			throws IOException {
		final byte[] ib = curs.tempId;
		for (;;) {
			readFully(pos
			int c = ib[0] & 0xff;
			final int type = (c >> 4) & 7;
			int shift = 4;
			int p = 1;
			while ((c & 0x80) != 0) {
				c = ib[p++] & 0xff;
				shift += 7;
			}

			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG:
				return type;

			case Constants.OBJ_OFS_DELTA: {
				c = ib[p++] & 0xff;
				long ofs = c & 127;
				while ((c & 128) != 0) {
					ofs += 1;
					c = ib[p++] & 0xff;
					ofs <<= 7;
					ofs += (c & 127);
				}
				pos = pos - ofs;
				continue;
			}

			case Constants.OBJ_REF_DELTA: {
				readFully(pos + p
				pos = findDeltaBase(ObjectId.fromRaw(ib));
				continue;
			}

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
		}
	}

