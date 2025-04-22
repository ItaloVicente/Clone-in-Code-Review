			b = Constants.CHARSET.encode(CharBuffer.wrap(prefix));
			pathLen = b.limit();
			path = new byte[Math.max(DEFAULT_PATH_SIZE, pathLen + 1)];
			b.get(path, 0, pathLen);
			if (path[pathLen - 1] != '/')
				path[pathLen++] = '/';
			pathOffset = pathLen;
