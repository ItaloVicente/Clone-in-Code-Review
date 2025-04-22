	private void copyAsIs2(PackOutputStream out
			WindowCursor curs) throws IOException
			StoredObjectRepresentationNotAvailableException {
		final CRC32 crc1 = new CRC32();
		final CRC32 crc2 = new CRC32();
		final byte[] buf = out.getCopyBuffer();

		readFully(src.copyOffset
		int c = buf[0] & 0xff;
		final int typeCode = (c >> 4) & 7;
		long inflatedLength = c & 15;
		int shift = 4;
		int headerCnt = 1;
		while ((c & 0x80) != 0) {
			c = buf[headerCnt++] & 0xff;
			inflatedLength += (c & 0x7f) << shift;
			shift += 7;
		}

		if (typeCode == Constants.OBJ_OFS_DELTA) {
			do {
				c = buf[headerCnt++] & 0xff;
			} while ((c & 128) != 0);
			crc1.update(buf
			crc2.update(buf
		} else if (typeCode == Constants.OBJ_REF_DELTA) {
			crc1.update(buf
			crc2.update(buf

			readFully(src.copyOffset + headerCnt
			crc1.update(buf
			crc2.update(buf
			headerCnt += 20;
