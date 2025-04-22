
			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.FETCH.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.schedule();
		return true;
	}

	private boolean needCheckout(CheckoutMode mode) {
		switch (mode) {
		case CHECKOUT_FETCH_HEAD:
