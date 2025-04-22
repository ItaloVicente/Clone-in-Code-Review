				long ofs = c & 127;
				while ((c & 128) != 0) {
					ofs += 1;
					c = ib[p++] & 0xff;
					ofs <<= 7;
					ofs += (c & 127);
				}
				return loadDelta(pos + p
