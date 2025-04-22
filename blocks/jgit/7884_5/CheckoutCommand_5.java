
	private void checkOptions() {
		if (checkoutStage != null && !isCheckoutIndex())
			throw new IllegalStateException(
					"Checking out ours/theirs is only possible when checking out index
							+ "not when switching branches.");
	}
