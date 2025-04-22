		return baseCommit.hashCode() ^ location.hashCode();
	}

	@Override
	public String toString() {
		return "ModelTree[baseCommit=" + baseCommit.getId() + ", location=" //$NON-NLS-1$ //$NON-NLS-2$
				+ getLocation() + "]"; //$NON-NLS-1$
