	public byte[] pathSegmentBuffer() {
		return raw;
	}

	public int pathSegmentOffset() {
		for (int i = currPtr; i < raw.length; ++i)
			if (raw[i] == ' ')
				return i + 1;
		throw new IllegalStateException();
	}

	public int pathSegmentLength() {
		int i = pathSegmentOffset();
		for (int j = i; j < raw.length; ++j)
			if (raw[j] == 0)
				return j - i;
		throw new IllegalStateException();
	}

