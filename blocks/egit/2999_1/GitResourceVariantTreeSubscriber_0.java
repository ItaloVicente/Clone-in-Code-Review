	private void enterFilteredPath(String path, TreeWalk tw) throws IOException {
		int subtreesLen = path.split("/").length; //$NON-NLS-1$
		for (int i = 0; i < subtreesLen; i++) {
			tw.next();
			tw.enterSubtree();
		}
	}

