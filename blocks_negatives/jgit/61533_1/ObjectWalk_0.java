		byte[] raw = reader.open(tree, OBJ_TREE).getCachedBytes();
		for (int ptr = 0; ptr < raw.length;) {
			byte c = raw[ptr];
			int mode = c - '0';
			for (;;) {
				c = raw[++ptr];
				if (' ' == c)
					break;
				mode <<= 3;
				mode += c - '0';
			}
			while (raw[++ptr] != 0) {
			}

			switch (mode >>> TYPE_SHIFT) {
