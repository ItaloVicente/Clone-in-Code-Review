	public OperationStatus getStatus() {
		return rv.getStatus();
	}

	public void set(Future<T> d, OperationStatus s) {
		rv.set(d, s);
