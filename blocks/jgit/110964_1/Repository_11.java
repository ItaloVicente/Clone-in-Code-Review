	@NonNull
	public File getSparseCheckoutFile() throws NoWorkTreeException {
		if (isBare())
			throw new NoWorkTreeException();
		return sparseCheckoutFile;
	}

