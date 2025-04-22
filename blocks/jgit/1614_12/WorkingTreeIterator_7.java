	private static class PerDirectoryAttributesNode extends AttributesNode {
		final Entry entry;

		PerDirectoryAttributesNode(Entry entry) {
			super(Collections.<AttributesRule> emptyList());
			this.entry = entry;
		}

		AttributesNode load() throws IOException {
			AttributesNode r = new AttributesNode();
			InputStream in = entry.openInputStream();
			try {
				r.parse(in);
			} finally {
				in.close();
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

	private static class InfoAttributesNode extends AttributesNode {
		final Repository repository;

		InfoAttributesNode(Repository repository) {
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

			File attributes = fs.resolve(repository.getDirectory()
			loadRulesFromFile(r

			return r.getRules().isEmpty() ? null : r;
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

