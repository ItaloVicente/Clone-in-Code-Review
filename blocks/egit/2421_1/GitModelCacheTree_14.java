		return getBaseId().hashCode() ^ getLocation().hashCode();
	}

	@Override
	public String toString() {
		return "GitModelTree[" + getLocation() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
