		if (dataLength <= buf.length) {
			out.writeHeader(src
			out.write(buf
		} else {
			out.writeHeader(src
			long pos = dataOffset;
			long cnt = dataLength;
			while (cnt > 0) {
				final int n = (int) Math.min(cnt
				readFully(pos
				crc2.update(buf
				out.write(buf
				pos += n;
				cnt -= n;
			}
			if (crc2.getValue() != expectedCRC) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().objectAtHasBadZlibStream
						getPackFile()));
			}
		}
