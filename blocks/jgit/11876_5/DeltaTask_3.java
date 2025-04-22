	private void runWorker(ObjectReader or
		DeltaWindow w = new DeltaWindow(block.config
				or
				block.list
		synchronized (this) {
			dw = w;
		}
		w.search();
		synchronized (this) {
			dw = null;
		}
	}

	synchronized Slice remaining() {
		if (!slices.isEmpty())
			return slices.getLast();
