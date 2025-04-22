		final Ref checkoutRef;
		if (branch == null)
			checkoutRef = guessHEAD(r);
		else
			checkoutRef = r.getAdvertisedRef(Constants.R_HEADS + branch);
		doCheckout(checkoutRef);
