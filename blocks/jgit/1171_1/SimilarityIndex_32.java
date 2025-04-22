	void hash(InputStream in
		byte[] buf = new byte[4096];
		int ptr = 0;
		int cnt = 0;

		while (0 < remaining) {
			int hash = 5381;

			int n = 0;
			do {
				if (ptr == cnt) {
					ptr = 0;
					cnt = in.read(buf
					if (cnt <= 0)
						throw new EOFException();
				}

				n++;
				int c = buf[ptr++] & 0xff;
				if (c == '\n')
					break;
				hash = (hash << 5) ^ c;
			} while (n < 64 && n < remaining);
			add(hash
			remaining -= n;
		}
	}

