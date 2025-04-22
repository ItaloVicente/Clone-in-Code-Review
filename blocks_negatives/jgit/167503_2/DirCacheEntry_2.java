		final int pathLen = path.length;
		os.write(info, infoOffset, len);
		os.write(path, 0, pathLen);

		final int actLen = len + pathLen;
		final int expLen = (actLen + 8) & ~7;
		if (actLen != expLen)
			os.write(nullpad, 0, expLen - actLen);
