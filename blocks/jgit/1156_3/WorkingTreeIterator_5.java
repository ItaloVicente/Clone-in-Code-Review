
	private static class PerDirectoryIgnoreNode extends IgnoreNode {
		final Entry entry;

		PerDirectoryIgnoreNode(Entry entry) {
			super(Collections.<IgnoreRule> emptyList());
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

	private static class RootIgnoreNode extends PerDirectoryIgnoreNode {
		final Repository repository;

		RootIgnoreNode(Entry entry
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

			File exclude = new File(repository.getDirectory()
			if (exclude.exists()) {
				FileInputStream in = new FileInputStream(exclude);
				try {
					r.parse(in);
				} finally {
					in.close();
				}
			}

			return r.getRules().isEmpty() ? null : r;
		}
	}
