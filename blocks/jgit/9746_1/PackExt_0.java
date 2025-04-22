	private final String ext;

	public PackExt(String ext) {
		this.ext = ext;
	}

	public String getExtension() {
		return ext;
	}

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
