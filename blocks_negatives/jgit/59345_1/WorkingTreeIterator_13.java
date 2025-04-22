	/** Magic type indicating we know rules exist, but they aren't loaded. */
	private static class PerDirectoryIgnoreNode extends IgnoreNode {
		final Entry entry;

		PerDirectoryIgnoreNode(Entry entry) {
			super(Collections.<FastIgnoreRule> emptyList());
			this.entry = entry;
		}

		IgnoreNode load() throws IOException {
			IgnoreNode r = new IgnoreNode();
			InputStream in = entry.openInputStream();
			try {
				r.parse(in);
			} finally {
				in.close();
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

	/** Magic type indicating there may be rules for the top level. */
	private static class RootIgnoreNode extends PerDirectoryIgnoreNode {
		final Repository repository;

		RootIgnoreNode(Entry entry, Repository repository) {
			super(entry);
			this.repository = repository;
		}

		@Override
		IgnoreNode load() throws IOException {
			IgnoreNode r;
			if (entry != null) {
				r = super.load();
				if (r == null)
					r = new IgnoreNode();
			} else {
				r = new IgnoreNode();
			}

			FS fs = repository.getFS();
			String path = repository.getConfig().get(CoreConfig.KEY)
					.getExcludesFile();
			if (path != null) {
				File excludesfile;
					excludesfile = fs.resolve(fs.userHome(), path.substring(2));
				else
					excludesfile = fs.resolve(null, path);
				loadRulesFromFile(r, excludesfile);
			}

			File exclude = fs.resolve(repository.getDirectory(),
					Constants.INFO_EXCLUDE);
			loadRulesFromFile(r, exclude);

			return r.getRules().isEmpty() ? null : r;
		}

		private static void loadRulesFromFile(IgnoreNode r, File exclude)
				throws FileNotFoundException, IOException {
			if (FS.DETECTED.exists(exclude)) {
				FileInputStream in = new FileInputStream(exclude);
				try {
					r.parse(in);
				} finally {
					in.close();
				}
			}
		}
	}

	/** Magic type indicating we know rules exist, but they aren't loaded. */
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

	/**
	 * Attributes node loaded from global system-wide file.
	 */
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
					attributesFile = fs.resolve(fs.userHome(),
							path.substring(2));
				else
					attributesFile = fs.resolve(null, path);
				loadRulesFromFile(r, attributesFile);
			}
			return r.getRules().isEmpty() ? null : r;
		}
	}

	/** Magic type indicating there may be rules for the top level. */
	private static class InfoAttributesNode extends AttributesNode {
		final Repository repository;

		InfoAttributesNode(Repository repository) {
			this.repository = repository;
		}

		AttributesNode load() throws IOException {
			AttributesNode r = new AttributesNode();

			FS fs = repository.getFS();

			File attributes = fs.resolve(repository.getDirectory(),
			loadRulesFromFile(r, attributes);

			return r.getRules().isEmpty() ? null : r;
		}

	}

	private static void loadRulesFromFile(AttributesNode r, File attrs)
			throws FileNotFoundException, IOException {
		if (attrs.exists()) {
			FileInputStream in = new FileInputStream(attrs);
			try {
				r.parse(in);
			} finally {
				in.close();
			}
		}
	}

