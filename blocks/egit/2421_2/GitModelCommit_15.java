		return baseCommit.hashCode() ^ getParent().hashCode();
	}

	@Override
	public String toString() {
		return "ModelCommit[" + baseCommit.getId() + "]"; //$NON-NLS-1$//$NON-NLS-2$
