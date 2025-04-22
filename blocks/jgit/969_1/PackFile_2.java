				if (quickCopy != null) {
					quickCopy.check(inf
				} else {
					long pos = dataOffset;
					long cnt = dataLength;
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
