	private String resolveReflogCheckout(int checkoutNo)
			throws IOException {
		List<ReflogEntry> reflogEntries = new ReflogReader(this
				.getReverseEntries();
		for (ReflogEntry entry : reflogEntries) {
			CheckoutEntry checkout = entry.parseCheckout();
			if (checkout != null)
				if (checkoutNo-- == 1)
					return checkout.getFromBranch();
		}
		return null;
	}

