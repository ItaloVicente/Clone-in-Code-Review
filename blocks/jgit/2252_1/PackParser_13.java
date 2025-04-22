	protected ObjectTypeAndSize readObjectHeader(ObjectTypeAndSize info)
			throws IOException {
		int c = readFrom(Source.DATABASE);
		info.type = (c >> 4) & 7;
		long sz = c & 15;
		int shift = 4;
		while ((c & 0x80) != 0) {
			c = readFrom(Source.DATABASE);
			sz += (c & 0x7f) << shift;
			shift += 7;
		}
		info.size = sz;

		switch (info.type) {
		case Constants.OBJ_COMMIT:
		case Constants.OBJ_TREE:
		case Constants.OBJ_BLOB:
		case Constants.OBJ_TAG:
			break;

		case Constants.OBJ_OFS_DELTA:
			c = readFrom(Source.DATABASE) & 0xff;
			while ((c & 128) != 0)
				c = readFrom(Source.DATABASE) & 0xff;
			break;

		case Constants.OBJ_REF_DELTA:
			crc.update(buf
			use(20);
			break;

		default:
			throw new IOException(MessageFormat.format(
					JGitText.get().unknownObjectType
		}
		return info;
	}

	private UnresolvedDelta removeBaseById(final AnyObjectId id) {
