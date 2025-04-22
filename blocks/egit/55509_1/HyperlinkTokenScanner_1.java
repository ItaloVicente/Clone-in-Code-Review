		int actualOffset = currentOffset;
		IToken token = scanToken();
		if (token != null && actualOffset < currentOffset) {
			return token;
		}
		currentOffset = actualOffset + 1;
		return defaultToken;
