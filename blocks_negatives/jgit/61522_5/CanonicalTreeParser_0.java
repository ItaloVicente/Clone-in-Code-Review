	/**
	 * {@link AttributesNode} implementation that provides lazy loading
	 */
	private class LazyLoadingAttributesNode extends AttributesNode {
		private final int idOffset;

		LazyLoadingAttributesNode(int idOffset) {
			super(Collections.<AttributesRule> emptyList());
			this.idOffset = idOffset;
