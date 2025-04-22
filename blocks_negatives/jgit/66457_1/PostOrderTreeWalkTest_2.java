		{
			final DirCacheBuilder b = tree.builder();

			b.add(makeFile("a"));
			b.add(makeFile("b/c"));
			b.add(makeFile("b/d"));
			b.add(makeFile("q"));

			b.finish();
			assertEquals(4, tree.getEntryCount());
