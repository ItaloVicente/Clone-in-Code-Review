	public CheckoutCommand addPaths(List<String> p) {
		checkCallable();
		this.paths.addAll(p);
		return this;
	}

