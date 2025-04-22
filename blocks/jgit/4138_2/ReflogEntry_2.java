
	public CheckoutEntry parseCheckout() {
		if (getComment().startsWith(CheckoutEntry.CHECKOUT_MOVING_FROM))
			return new CheckoutEntry(this);
		else
			return null;
	}
