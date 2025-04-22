		final Ref checkoutRef;
		if (branch == null)
			checkoutRef = guessHEAD(r);
		else {
			checkoutRef = r.getAdvertisedRef(Constants.R_HEADS + branch);
			if (checkoutRef == null)
				throw die(MessageFormat.format(CLIText.get().noSuchRemoteRef
						branch));
		}
		doCheckout(checkoutRef);
