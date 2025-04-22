	private void enterFilteredPath(String path, TreeWalk tw) throws IOException {
		for (int i = 0; i < subtreesLen; i++) {
			tw.next();
			tw.enterSubtree();
		}
	}

