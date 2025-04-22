		final TreeWalk tw = new TreeWalk(db);
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
			return rw.lookupAny(entid, entmode.getObjectType());
		}
		fail("Can't find " + path + " in tree " + tree.name());
