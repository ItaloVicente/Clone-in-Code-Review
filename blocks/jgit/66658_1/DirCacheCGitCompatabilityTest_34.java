			try (final TreeWalk tw = new TreeWalk(db)) {
				tw.setRecursive(true);
				tw.addTree(new DirCacheIterator(dc));
				while (rItr.hasNext()) {
					final DirCacheIterator dcItr;

					assertTrue(tw.next());
					dcItr = tw.getTree(0
					assertNotNull(dcItr);

					assertEqual(rItr.next()
				}
