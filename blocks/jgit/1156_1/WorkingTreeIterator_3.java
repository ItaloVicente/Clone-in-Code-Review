	protected void initRootIterator(Repository repo) throws IOException {
		File exclude = new File(repo.getDirectory()
		if (exclude.isFile() && exclude.canRead()) {
			if (ignoreNode == null)
				ignoreNode = new IgnoreNode();
			FileInputStream in = new FileInputStream(exclude);
			try {
				ignoreNode.parse(in);
			} finally {
				in.close();
			}
		}
	}

