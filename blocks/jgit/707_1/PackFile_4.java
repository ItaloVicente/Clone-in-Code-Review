	private void putOpenHandle(OpenFile fc) {
		AvailableFileNode r
		do {
			r = fds.get();
			n = new AvailableFileNode(r
		} while (!fds.compareAndSet(r
	}
