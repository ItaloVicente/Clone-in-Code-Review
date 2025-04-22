			int headerCnt = loader.headerSize;
			while (headerCnt > 0) {
				final int toRead = Math.min(headerCnt, buf.length);
				readFully(objectOffset, buf, 0, toRead, curs);
				crc.update(buf, 0, toRead);
				headerCnt -= toRead;
			}
