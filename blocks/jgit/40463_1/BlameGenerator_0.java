
	@Override
	public void close() {
		revPool.close();
		queue = null;
		outCandidate = null;
		outRegion = null;
	}
