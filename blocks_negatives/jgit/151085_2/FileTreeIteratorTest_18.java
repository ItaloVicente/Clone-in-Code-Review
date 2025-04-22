		TreeWalk tw = new TreeWalk(db);
		FileTreeIterator fti = new FileTreeIterator(trash, db.getFS(), db
				.getConfig().get(WorkingTreeOptions.KEY));
		tw.addTree(fti);
		DirCacheIterator dci = new DirCacheIterator(db.readDirCache());
		tw.addTree(dci);
		fti.setDirCacheIterator(tw, 1);
		while (tw.next() && !tw.getPathString().equals("file")) {
		}
		assertEquals(MetadataDiff.EQUAL, fti.compareMetadata(dce));
		ObjectId fromRaw = ObjectId.fromRaw(fti.idBuffer(), fti.idOffset());
		assertEquals("6b584e8ece562ebffc15d38808cd6b98fc3d97ea",
				fromRaw.getName());
		try (ObjectReader objectReader = db.newObjectReader()) {
			assertFalse(fti.isModified(dce, false, objectReader));
