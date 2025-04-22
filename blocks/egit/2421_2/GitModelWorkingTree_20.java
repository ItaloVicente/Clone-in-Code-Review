	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelWorkingTree) {
			GitModelCache left = (GitModelCache) obj;
			return left.baseCommit.equals(baseCommit)
					&& left.getParent().equals(getParent());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31 + baseCommit.hashCode() ^ getParent().hashCode();
	}

	@Override
	public String toString() {
		return "ModelWorkingTree"; //$NON-NLS-1$
	}

