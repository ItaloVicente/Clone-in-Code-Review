		TreeWalk tw = new TreeWalk(reader);
		PathFilter f = PathFilter.create(path);
		tw.setFilter(f);
		tw.reset(trees);
		tw.setRecursive(false);

		while (tw.next()) {
			if (f.isDone(tw)) {
				return tw;
			} else if (tw.isSubtree()) {
				tw.enterSubtree();
			}
		}
		return null;
