	public boolean isDryRun() {
		return dryRun;
	}

	public FetchCommand setDryRun(boolean dryRun) {
		checkCallable();
		this.dryRun = dryRun;
		return this;
	}

	public boolean isThin() {
		return thin;
	}

	public FetchCommand setThin(boolean thin) {
		checkCallable();
		this.thin = thin;
		return this;
	}

