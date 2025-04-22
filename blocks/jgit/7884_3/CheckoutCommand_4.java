	public CheckoutCommand setStage(Stage stage) {
		checkCallable();
		this.checkoutStage = stage;
		checkOptions();
		return this;
	}

