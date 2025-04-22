	public CheckoutCommand setOurs(boolean ours) {
		checkCallable();
		this.ours = ours;
		checkOptions();
		return this;
	}

	public CheckoutCommand setTheirs(boolean theirs) {
		checkCallable();
		this.theirs = theirs;
		checkOptions();
		return this;
	}

