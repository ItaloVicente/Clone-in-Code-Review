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

	private static class RootAttributesNode extends PerDirectoryAttributesNode {
		final Repository repository;

		RootAttributesNode(Entry entry
			super(entry);
			this.repository = repository;
		}

		@Override
		AttributesNode load() throws IOException {
			AttributesNode r;
			if (entry != null) {
				r = super.load();
				if (r == null)
					r = new AttributesNode();
			} else {
				r = new AttributesNode();
			}

			FS fs = repository.getFS();
			String path = repository.getConfig().get(CoreConfig.KEY)
					.getAttributesFile();
			if (path != null) {
				File attributesFile;
					attributesFile = fs.resolve(fs.userHome()
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

