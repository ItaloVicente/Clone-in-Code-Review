		try (TreeWalk tw = new TreeWalk(pool.getObjectReader())) {
			tw.setFilter(PathFilterGroup.createFromStrings(Collections
					.singleton(path)));
			tw.reset(tree);
			while (tw.next()) {
				if (tw.isSubtree() && !path.equals(tw.getPathString())) {
					tw.enterSubtree();
					continue;
				}
				final ObjectId entid = tw.getObjectId(0);
				final FileMode entmode = tw.getFileMode(0);
				return pool.lookupAny(entid
