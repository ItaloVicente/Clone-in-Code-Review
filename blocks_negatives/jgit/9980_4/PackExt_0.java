	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PackExt) {
			return ((PackExt) obj).getExtension().equals(getExtension());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getExtension().hashCode();
