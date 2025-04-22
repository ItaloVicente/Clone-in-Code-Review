		try {
			return doCheckout();
		} finally {
			dc.unlock();
		}
	}

	private boolean doCheckout() throws CorruptObjectException
			MissingObjectException
			CheckoutConflictException
