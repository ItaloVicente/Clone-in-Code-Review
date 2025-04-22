		return this.toString();
	}

	private String getPostalCode() {
		if (postalCode == null)
			postalCode = POSTALCODE_DEFAULT;
		return postalCode;
	}

	@Override
