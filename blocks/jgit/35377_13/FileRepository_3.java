
	@Override
	public AttributesNodeProvider newAttributesNodeProvider() {
		return new AttributesNodeProviderImpl(this);
	}

	static class AttributesNodeProviderImpl implements
			AttributesNodeProvider {

		private AttributesNode infoAttributesNode;

		private AttributesNode globalAttributesNode;

		protected AttributesNodeProviderImpl(Repository repo) {
			infoAttributesNode = new InfoAttributesNode(repo);
			globalAttributesNode = new GlobalAttributesNode(repo);
		}

		public AttributesNode getInfoAttributesNode() throws IOException {
			if (infoAttributesNode instanceof InfoAttributesNode)
				infoAttributesNode = ((InfoAttributesNode) infoAttributesNode)
						.load();
			return infoAttributesNode;
		}

		public AttributesNode getGlobalAttributesNode() throws IOException {
			if (globalAttributesNode instanceof GlobalAttributesNode)
				globalAttributesNode = ((GlobalAttributesNode) globalAttributesNode)
						.load();
			return globalAttributesNode;
		}

		static void loadRulesFromFile(AttributesNode r
				throws FileNotFoundException
			if (attrs.exists()) {
				FileInputStream in = new FileInputStream(attrs);
				try {
					r.parse(in);
				} finally {
					in.close();
				}
			}
		}

	}

