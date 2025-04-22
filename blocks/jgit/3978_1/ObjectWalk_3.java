		pathLen = 0;

		TreeVisit tv = currVisit;
		while (tv != null) {
			byte[] buf = tv.buf;
			for (int ptr = tv.ptr; ptr < buf.length;) {
				int startPtr = ptr;
				ptr = findObjectId(buf
				idBuffer.fromRaw(buf
				ptr += ID_SZ;

				RevObject obj = objects.get(idBuffer);
				if (obj != null && (obj.flags & SEEN) != 0)
					continue;
