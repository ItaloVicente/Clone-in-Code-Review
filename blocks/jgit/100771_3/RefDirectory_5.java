	@Override
	public PackedBatchRefUpdate newBatchUpdate() {
		return new PackedBatchRefUpdate(this);
	}

	@Override
	public boolean performsAtomicTransactions() {
		return true;
	}

