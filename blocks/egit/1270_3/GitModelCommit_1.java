	public Image getImage() {
		return null;
	}

	public int getKind() {
		if (kind == -1)
			calculateKind(getAncestorSha1(), getBaseSha1(), getRemoteSha1());

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

	protected String getAncestorSha1() {
		return ancestorCommit.getId().getName();
	}

	protected String getBaseSha1() {
		return baseCommit.getId().getName();
	}

	protected String getRemoteSha1() {
		return remoteCommit.getId().getName();
	}

