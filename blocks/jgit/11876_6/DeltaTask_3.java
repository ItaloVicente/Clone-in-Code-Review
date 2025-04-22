	DeltaWindow initWindow(Slice s) {
		DeltaWindow w = new DeltaWindow(block.config
				or
				block.list
		synchronized (this) {
			dw = w;
		}
		return w;
	}

	private void runWindow(DeltaWindow w) throws IOException {
		try {
			w.search();
		} finally {
			synchronized (this) {
				dw = null;
			}
		}
	}

	synchronized Slice remaining() {
		if (!slices.isEmpty())
			return slices.getLast();
