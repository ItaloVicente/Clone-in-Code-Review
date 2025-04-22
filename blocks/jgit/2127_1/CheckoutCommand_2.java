
	public CheckoutResult getResult() {
		if (status == null)
			return CheckoutResult.NOT_TRIED_RESULT;
		return status;
	}
