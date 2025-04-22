
	@Override
	public void swapIndex(IWorkingSet one, IWorkingSet other) {
		super.swapIndex(one, other);
		saveState();
	}
