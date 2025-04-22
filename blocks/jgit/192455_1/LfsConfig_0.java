		delegate = null;
	}

	private Config getDelegate() throws IOException {
		if (delegate == null) {
			delegate = this.load();
		}
		return delegate;
