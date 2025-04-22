		TreeVisit tv = currVisit;
		if (tv == null)
			return 0;

		int nameEnd = tv.nameEnd;
		if (nameEnd == 0) {
			tv = tv.parent;
			if (tv == null)
				return 0;
			nameEnd = tv.nameEnd;
		}

		byte[] buf;
		int ptr;

		if (16 <= (nameEnd - tv.namePtr)) {
			buf = tv.buf;
			ptr = nameEnd - 16;
		} else {
			nameEnd = pathLen;
			if (nameEnd == 0) {
				nameEnd = updatePathBuf(currVisit);
				pathLen = nameEnd;
			}
			buf = pathBuf;
			ptr = Math.max(0
		}

		int hash = 0;
		for (; ptr < nameEnd; ptr++) {
			byte c = buf[ptr];
			if (c != ' ')
				hash = (hash >>> 2) + (c << 24);
		}
		return hash;
