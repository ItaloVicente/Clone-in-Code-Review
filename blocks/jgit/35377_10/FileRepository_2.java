
	@Override
	public AttributeNodeProvider newAttributeNodeProvider() {
		return new AttributeNodeProviderImpl(this);
	}

	private static class AttributeNodeProviderImpl implements
			AttributeNodeProvider {

		private AttributesNode infoAttributeNode;

		private AttributesNode globalAttributeNode;

		protected AttributeNodeProviderImpl(Repository repo) {
			infoAttributeNode = new InfoAttributesNode(repo);
			globalAttributeNode = new GlobalAttributesNode(repo);
		}

		public AttributesNode getInfoAttributesNode() throws IOException {
			if (infoAttributeNode instanceof InfoAttributesNode)
				infoAttributeNode = ((InfoAttributesNode) infoAttributeNode)
						.load();
			return infoAttributeNode;
		}

		public AttributesNode getGlobalAttributesNode() throws IOException {
			if (globalAttributeNode instanceof GlobalAttributesNode)
				globalAttributeNode = ((GlobalAttributesNode) globalAttributeNode)
						.load();
			return globalAttributeNode;
		}

		private static class InfoAttributesNode extends AttributesNode {
			final Repository repository;

			InfoAttributesNode(Repository repository) {
				this.repository = repository;
			}

			AttributesNode load() throws IOException {
				AttributesNode r = new AttributesNode();

				FS fs = repository.getFS();

				File attributes = fs.resolve(repository.getDirectory()
				loadRulesFromFile(r

				return r.getRules().isEmpty() ? null : r;
			}

		}

		private static class GlobalAttributesNode extends AttributesNode {
			final Repository repository;

			GlobalAttributesNode(Repository repository) {
				this.repository = repository;
			}

			AttributesNode load() throws IOException {
				AttributesNode r = new AttributesNode();

				FS fs = repository.getFS();
				String path = repository.getConfig().get(CoreConfig.KEY)
						.getAttributesFile();
				if (path != null) {
					File attributesFile;
						attributesFile = fs.resolve(fs.userHome()
								path.substring(2));
					else
						attributesFile = fs.resolve(null
					loadRulesFromFile(r
				}
				return r.getRules().isEmpty() ? null : r;
			}
		}

		private static void loadRulesFromFile(AttributesNode r
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

