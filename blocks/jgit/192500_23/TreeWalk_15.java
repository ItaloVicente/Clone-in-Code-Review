	public static boolean walkToPath(TreeWalk tw
			throws IOException {
		PathFilter f = PathFilter.create(path);
		tw.setFilter(f);
		tw.setRecursive(false);

		while (tw.next()) {
			if (f.isDone(tw)) {
				return true;
			} else if (tw.isSubtree()) {
				tw.enterSubtree();
			}
		}
		return false;
	}

