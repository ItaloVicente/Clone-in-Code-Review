	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelCache
				&& !(obj instanceof GitModelWorkingTree)) {
			GitModelCache left = (GitModelCache) obj;
			return left.baseCommit.equals(baseCommit)
					&& left.getParent().equals(getParent());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return baseCommit.hashCode() ^ getParent().hashCode();
	}

	@Override
	public String toString() {
		return "ModelCache"; //$NON-NLS-1$
	}

