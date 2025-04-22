			readSome(in
			int c = hdr[0] & 0xff;
			long size = c & 15;
			int shift = 4;
			int p = 1;
			while ((c & 0x80) != 0) {
				c = hdr[p++] & 0xff;
				size += ((long) (c & 0x7f)) << shift;
				shift += 7;
			}
			return size;
