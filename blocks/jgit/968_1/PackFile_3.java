			crc1.update(buf
			crc2.update(buf
		}

		final long dataOffset = src.copyOffset + headerCnt;
		final long dataLength;
		final long expectedCRC;

		try {
			dataLength = findEndOffset(src.copyOffset) - dataOffset;

			if (idx().hasCRC32Support()) {
				expectedCRC = idx().findCRC32(src);

				long pos = dataOffset;
				long cnt = dataLength;
				while (cnt > 0) {
					final int n = (int) Math.min(cnt
					readFully(pos
					crc1.update(buf
					pos += n;
					cnt -= n;
				}
				if (crc1.getValue() != expectedCRC) {
					setCorrupt(src.copyOffset);
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							src.copyOffset
				}
			} else {
				long pos = dataOffset;
				long cnt = dataLength;
				Inflater inf = curs.inflater();
				byte[] tmp = new byte[1024];
				while (cnt > 0) {
					final int n = (int) Math.min(cnt
					readFully(pos
					crc1.update(buf
					inf.setInput(buf
					while (inf.inflate(tmp
						continue;
					pos += n;
					cnt -= n;
				}
				if (!inf.finished()) {
					setCorrupt(src.copyOffset);
					throw new EOFException(MessageFormat.format(
							JGitText.get().shortCompressedStreamAt
							src.copyOffset));
				}
				expectedCRC = crc1.getValue();
