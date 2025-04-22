		if (idx.hasCRC32Support()) {
			final CRC32 crc = new CRC32();
			int headerCnt = loader.headerSize;
			while (headerCnt > 0) {
				final int toRead = Math.min(headerCnt, buf.length);
				readFully(objectOffset, buf, 0, toRead, curs);
				crc.update(buf, 0, toRead);
				headerCnt -= toRead;
			}
			final CheckedOutputStream crcOut = new CheckedOutputStream(out, crc);
			copyToStream(dataOffset, buf, sz, crcOut, curs);
			final long computed = crc.getValue();

			final ObjectId id = findObjectForOffset(objectOffset);
			final long expected = idx.findCRC32(id);
			if (computed != expected)
				throw new CorruptObjectException(MessageFormat.format(
						JGitText.get().objectAtHasBadZlibStream, objectOffset, getPackFile()));
