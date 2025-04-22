	@Test
	public void testIterator()
			throws IllegalStateException, IOException, InterruptedException {
		TreeSet<Long> modTimes = new TreeSet<>();
		File lastFile = null;
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkTree(), "0." + i);
			FileUtils.createNewFile(lastFile);
			if (i == 5)
				fsTick(lastFile);
		}
		modTimes.add(valueOf(fsTick(lastFile)));
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkTree(), "1." + i);
			FileUtils.createNewFile(lastFile);
		}
		modTimes.add(valueOf(fsTick(lastFile)));
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkTree(), "2." + i);
			FileUtils.createNewFile(lastFile);
			if (i % 4 == 0)
				fsTick(lastFile);
		}
		FileTreeIteratorWithTimeControl fileIt = new FileTreeIteratorWithTimeControl(
				db, modTimes);
		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(fileIt);
			tw.setRecursive(true);
			FileTreeIterator t;
			long t0 = 0;
			for (int i = 0; i < 10; i++) {
				assertTrue(tw.next());
				t = tw.getTree(0, FileTreeIterator.class);
				if (i == 0) {
					t0 = t.getEntryLastModified();
				} else {
					assertEquals(t0, t.getEntryLastModified());
				}
			}
			long t1 = 0;
			for (int i = 0; i < 10; i++) {
				assertTrue(tw.next());
				t = tw.getTree(0, FileTreeIterator.class);
				if (i == 0) {
					t1 = t.getEntryLastModified();
					assertTrue(t1 > t0);
				} else {
					assertEquals(t1, t.getEntryLastModified());
				}
			}
			long t2 = 0;
			for (int i = 0; i < 10; i++) {
				assertTrue(tw.next());
				t = tw.getTree(0, FileTreeIterator.class);
				if (i == 0) {
					t2 = t.getEntryLastModified();
					assertTrue(t2 > t1);
				} else {
					assertEquals(t2, t.getEntryLastModified());
				}
			}
		}
	}
