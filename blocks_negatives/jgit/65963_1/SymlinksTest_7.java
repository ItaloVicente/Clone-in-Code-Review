		TreeWalk tw = new TreeWalk(db);
		tw.addTree(commit1.getTree());
		tw.addTree(commit2.getTree());
		List<DiffEntry> scan = DiffEntry.scan(tw);
		assertEquals(1, scan.size());
		assertEquals(FileMode.MISSING, scan.get(0).getNewMode());
		assertEquals(FileMode.SYMLINK, scan.get(0).getOldMode());
