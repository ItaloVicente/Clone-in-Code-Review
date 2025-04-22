	public Image getImage() {
		return null;
	}

	public int getKind() {
		if (kind == -1 || kind == LEFT || kind == RIGHT)
			calculateKind(getBaseObjectId(), getRemoteObjectId());

		return kind;
	}

	public ITypedElement getAncestor() {
		return null;
	}

	public ITypedElement getLeft() {
		return null;
	}

	public ITypedElement getRight() {
		return null;
	}

	public void addCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void removeCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void copy(boolean leftToRight) {
	}

