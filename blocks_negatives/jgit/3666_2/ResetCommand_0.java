			final TreeWalk tw = new TreeWalk(repo);
			tw.addTree(new DirCacheIterator(dc));
			tw.addTree(commit.getTree());
			tw.setFilter(PathFilterGroup.createFromStrings(filepaths));

			while (tw.next()) {
				final String path = tw.getPathString();
				final CanonicalTreeParser tree = tw.getTree(1,
						CanonicalTreeParser.class);
				if (tree == null)
