		try {
			byte[] idBuf = new byte[Constants.OBJECT_ID_LENGTH];
			TreeWalk tw = new TreeWalk(reader);
			tw.reset();
			tw.setRecursive(true);
			tw.addTree(treeId);

			notes.clear();

			while (tw.next()) {
				ObjectId pathId = pathToId(idBuf, tw.getRawPath());
				if (pathId != null)
					notes.add(new Note(pathId, tw.getObjectId(0)));
			}
		} finally {
			reader.release();
		}
	}

	private static ObjectId pathToId(byte[] rawIdBuf, byte[] path) {
		int r = 0;

		for (int i = 0; i < path.length; i++) {
			byte c = path[i];

			if (c == '/')
				continue;

			if (path.length < i + 2)
				return null;

			if (r == Constants.OBJECT_ID_STRING_LENGTH)
				return null;

			int high, low;
			try {
				high = RawParseUtils.parseHexInt4(c);
				low = RawParseUtils.parseHexInt4(path[++i]);
			} catch (ArrayIndexOutOfBoundsException notHex) {
				return null;
			}

			rawIdBuf[r++] = (byte) ((high << 4) | low);
		}

		if (r == Constants.OBJECT_ID_LENGTH)
			return ObjectId.fromRaw(rawIdBuf);
		else
			return null;
