
		if (aPos < aEnd)
			return (raw[aPos] & 0xff) - lastPathChar(bMode);
		if (bPos < bEnd)
			return lastPathChar(aMode) - (raw[bPos] & 0xff);
		return 0;
