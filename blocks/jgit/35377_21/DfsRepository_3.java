
	@Override
	public AttributesNodeProvider createAttributesNodeProvider() {
		return new EmptyAttributesNodeProvider();
	}

	private static class EmptyAttributesNodeProvider implements
			AttributesNodeProvider {
		private EmptyAttributesNode emptyAttributesNode = new EmptyAttributesNode();

		public AttributesNode getInfoAttributesNode() throws IOException {
			return emptyAttributesNode;
		}

		public AttributesNode getGlobalAttributesNode() throws IOException {
			return emptyAttributesNode;
		}

		private static class EmptyAttributesNode extends AttributesNode {

			public EmptyAttributesNode() {
				super(Collections.<AttributesRule> emptyList());
			}

			@Override
			public void parse(InputStream in) throws IOException {
			}
		}
	}
