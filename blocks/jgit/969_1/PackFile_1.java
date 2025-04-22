				if (quickCopy != null) {
					quickCopy.crc32(crc1
				} else {
					long pos = dataOffset;
					long cnt = dataLength;
					while (cnt > 0) {
						final int n = (int) Math.min(cnt
						readFully(pos
						crc1.update(buf
						pos += n;
						cnt -= n;
					}
