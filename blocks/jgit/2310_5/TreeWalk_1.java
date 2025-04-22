		TreeWalk tw = new TreeWalk(reader);
		PathFilter f = PathFilter.create(path);
		tw.setFilter(f);
		tw.reset(trees);
		tw.setRecursive(false);

		for (;;) {
			if (!tw.next()) {
				return null;
			} else if (f.isDone(tw)) {
				return tw;
			} else if (tw.isSubtree()) {
				tw.enterSubtree();
			} else {
				return null;
			}
		}
