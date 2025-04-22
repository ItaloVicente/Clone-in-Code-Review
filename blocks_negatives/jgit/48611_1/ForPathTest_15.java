		ObjectReader or = db.newObjectReader();
		ObjectInserter oi = db.newObjectInserter();

		DirCacheEntry aDotB = createEntry("a.b", EXECUTABLE_FILE);
		b0.add(aDotB);
		DirCacheEntry aSlashB = createEntry("a/b", REGULAR_FILE);
		b0.add(aSlashB);
		DirCacheEntry aSlashCSlashD = createEntry("a/c/d", REGULAR_FILE);
		b0.add(aSlashCSlashD);
		DirCacheEntry aZeroB = createEntry("a0b", SYMLINK);
		b0.add(aZeroB);
		b0.finish();
		assertEquals(4, tree0.getEntryCount());
		ObjectId tree = tree0.writeTree(oi);

		TreeWalk tw = new TreeWalk(or);
		tw.addTree(tree);
		ObjectId a = null;
		ObjectId aSlashC = null;
		while (tw.next()) {
			if (tw.getPathString().equals("a")) {
				a = tw.getObjectId(0);
				tw.enterSubtree();
				while (tw.next()) {
					if (tw.getPathString().equals("a/c")) {
						aSlashC = tw.getObjectId(0);
						break;
