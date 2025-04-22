
	@Override
	public AttributeNodeProvider newAttributeNodeProvider() {
		return new EmptyAttributeNodeProvider();
	}

	private static class EmptyAttributeNodeProvider implements
			AttributeNodeProvider {
		private EmptyAttributeNode emptyAttributeNode = new EmptyAttributeNode();

		public AttributesNode getInfoAttributesNode() throws IOException {
			return emptyAttributeNode;
		}

		public AttributesNode getGlobalAttributesNode() throws IOException {
			return emptyAttributeNode;
		}

		private static class EmptyAttributeNode extends AttributesNode {

			public EmptyAttributeNode() {
				super(Collections.<AttributesRule> emptyList());
			}

			@Override
			public void parse(InputStream in) throws IOException {
			}
		}
	}
