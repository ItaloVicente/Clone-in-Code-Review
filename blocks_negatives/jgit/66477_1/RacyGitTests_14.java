		NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(fileIt);
		tw.setRecursive(true);
		FileTreeIterator t;
		long t0 = 0;
		for (int i = 0; i < 10; i++) {
			assertTrue(tw.next());
			t = tw.getTree(0, FileTreeIterator.class);
			if (i == 0)
				t0 = t.getEntryLastModified();
			else
				assertEquals(t0, t.getEntryLastModified());
		}
		long t1 = 0;
		for (int i = 0; i < 10; i++) {
			assertTrue(tw.next());
			t = tw.getTree(0, FileTreeIterator.class);
			if (i == 0) {
				t1 = t.getEntryLastModified();
				assertTrue(t1 > t0);
			} else
				assertEquals(t1, t.getEntryLastModified());
		}
		long t2 = 0;
		for (int i = 0; i < 10; i++) {
			assertTrue(tw.next());
			t = tw.getTree(0, FileTreeIterator.class);
			if (i == 0) {
				t2 = t.getEntryLastModified();
				assertTrue(t2 > t1);
			} else
				assertEquals(t2, t.getEntryLastModified());
