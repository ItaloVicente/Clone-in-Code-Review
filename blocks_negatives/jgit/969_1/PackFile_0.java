
				long pos = dataOffset;
				long cnt = dataLength;
				while (cnt > 0) {
					final int n = (int) Math.min(cnt, buf.length);
					readFully(pos, buf, 0, n, curs);
					crc1.update(buf, 0, n);
					pos += n;
					cnt -= n;
