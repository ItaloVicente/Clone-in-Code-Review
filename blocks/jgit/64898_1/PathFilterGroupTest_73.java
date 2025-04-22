	TreeWalk fakeWalkAtSubtree(final String path) throws IOException {
		DirCache dc = DirCache.newInCore();
		DirCacheEditor dce = dc.editor();
		dce.add(new DirCacheEditor.PathEdit(path + "/README") {
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
			}
		});
		dce.finish();

		TreeWalk ret = new TreeWalk((ObjectReader) null);
		ret.addTree(new DirCacheIterator(dc));
		ret.next();
		while (!path.equals(ret.getPathString())) {
			if (ret.isSubtree()) {
				ret.enterSubtree();
			}
			ret.next();
		}
		return ret;
	}
