			if (!validate) {
				long pos = dataOffset;
				long cnt = dataLength;
				while (cnt > 0) {
					final int n = (int) Math.min(cnt
					readFully(pos
					pos += n;
					cnt -= n;
				}
			}
