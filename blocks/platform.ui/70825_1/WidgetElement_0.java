	public final String getAttribute(String attr) {
		return Objects.toString(internalGetAttribute(attr), "");
	}

	@Override
	public final boolean hasAttribute(String attr) {
		return internalGetAttribute(attr) != null;
	}

	protected String internalGetAttribute(String attr) {
