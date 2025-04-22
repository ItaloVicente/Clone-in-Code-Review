		return setForceRefUpdate(force);
	}

	public CheckoutCommand setForceRefUpdate(boolean forceRefUpdate) {
		checkCallable();
		this.forceRefUpdate = forceRefUpdate;
		return this;
	}

	public CheckoutCommand setForced(boolean forced) {
