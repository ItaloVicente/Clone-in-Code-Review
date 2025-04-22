				while (cnt > 0) {
					final int n = (int) Math.min(cnt, buf.length);
					readFully(pos, buf, 0, n, curs);
					crc1.update(buf, 0, n);
					inf.setInput(buf, 0, n);
					while (inf.inflate(tmp, 0, tmp.length) > 0)
						continue;
					pos += n;
					cnt -= n;
