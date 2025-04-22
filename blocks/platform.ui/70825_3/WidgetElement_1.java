	public final String getAttribute(String attr) {
		Supplier<String> attribute = internalGetAttribute(attr);
		return attribute != null ? attribute.get() : "";
	}

	@Override
	public final boolean hasAttribute(String attr) {
		return internalGetAttribute(attr) != null;
	}

	protected Supplier<String> internalGetAttribute(String attr) {
