	public int getDepth() {
		if (options == null) {
			throw new IllegalStateException("do not call getDepth() before recvWants()");
		}
		return depth;
	}

