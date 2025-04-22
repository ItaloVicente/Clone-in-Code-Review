	protected void addCheckoutMetadata(String path
			throws IOException {
		if (checkoutMetadata != null) {
			EolStreamType eol = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKOUT_OP
			CheckoutMetadata data = new CheckoutMetadata(eol
					tw.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE));
			checkoutMetadata.put(path
		}
	}

	protected void addToCheckout(String path
			Attributes attributes) throws IOException {
		toBeCheckedOut.put(path
		addCheckoutMetadata(path
	}

	protected void addDeletion(String path
			Attributes attributes) throws IOException {
		toBeDeleted.add(path);
		if (isFile) {
			addCheckoutMetadata(path
		}
	}

