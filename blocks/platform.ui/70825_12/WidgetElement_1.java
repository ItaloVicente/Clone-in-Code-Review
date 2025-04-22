	public final String getAttribute(String attr) {
		Supplier<String> attribute = internalGetAttribute(attr);
		if (attribute != null) {
			String attributeValue = attribute.get();
			Assert.isNotNull(attributeValue);
			return attributeValue;
		}
		return "";
	}

	@Override
	public final boolean hasAttribute(String attr) {
		return internalGetAttribute(attr) != null;
	}

	protected Supplier<String> internalGetAttribute(String attr) {
