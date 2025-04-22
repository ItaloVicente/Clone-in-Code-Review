	private static int parseMode(byte[] buf
		int mode = buf[startPtr] - '0';
		for (;;) {
			byte c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';

			c = buf[++startPtr];
			if (' ' == c)
				break;
			mode <<= 3;
			mode += c - '0';
		}

		tv.ptr = recEndPtr;
		tv.namePtr = startPtr + 1;
		tv.nameEnd = recEndPtr - (ID_SZ + 1);
		return mode;
