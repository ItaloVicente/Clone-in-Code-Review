		if (pathLen == 0) {
			pathLen = updatePathBuf(currVisit);
			if (pathLen == 0)
				return null;
		}
		return RawParseUtils.decode(pathBuf
