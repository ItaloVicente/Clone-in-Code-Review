	@Override
	public ITypedElement getAncestor() {
		createCompareInput();
		return compareInput.getAncestor();
	}

	@Override
	public ITypedElement getLeft() {
		createCompareInput();
		return compareInput.getLeft();
	}

	@Override
	public ITypedElement getRight() {
		createCompareInput();
		return compareInput.getRight();
	}

