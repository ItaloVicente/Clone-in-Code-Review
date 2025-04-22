		if (pathLen == 0)
			pathLen = updatePathBuf(currVisit);
		return pathLen;
	}

	private int updatePathBuf(TreeVisit tv) {
		if (tv == null)
			return 0;

		int nameEnd = tv.nameEnd;
		if (nameEnd == 0)
			return updatePathBuf(tv.parent);

		int ptr = tv.pathLen;
		if (ptr == 0) {
			ptr = updatePathBuf(tv.parent);
			if (ptr == pathBuf.length)
				growPathBuf(ptr);
			if (ptr != 0)
				pathBuf[ptr++] = '/';
			tv.pathLen = ptr;
		}

		int namePtr = tv.namePtr;
		int nameLen = nameEnd - namePtr;
		int end = ptr + nameLen;
		while (pathBuf.length < end)
			growPathBuf(ptr);
		System.arraycopy(tv.buf
		return end;
	}

	private void growPathBuf(int ptr) {
		byte[] newBuf = new byte[pathBuf.length << 1];
		System.arraycopy(pathBuf
		pathBuf = newBuf;
