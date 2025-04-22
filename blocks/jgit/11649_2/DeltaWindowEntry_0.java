	int chainLength() {
		int n = object.getChainLength();
		if (0 < n)
			object.setChainLength(0);
		return n;
	}

