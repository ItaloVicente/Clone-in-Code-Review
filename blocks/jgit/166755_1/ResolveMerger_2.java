	protected void addCheckoutMetadata(String path
			Attributes attributes) throws IOException {
		if (checkoutMetadata != null) {
			EolStreamType eol = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKOUT_OP
			if (EolStreamType.AUTO_LF.equals(eol) && isCrLfText(blobId)) {
				eol = EolStreamType.AUTO_CRLF;
			}
			addCheckoutMetadata(path
					tw.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
		}
	}

	private void addCheckoutMetadata(String path
			@NonNull CheckoutMetadata data) {
		if (checkoutMetadata != null) {
			checkoutMetadata.put(path
		}
	}

